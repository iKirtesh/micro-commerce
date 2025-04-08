package com.kirtesh.ecommerce.kafka;

import com.kirtesh.ecommerce.email.EmailService;
import com.kirtesh.ecommerce.kafka.order.OrderConfirmation;
import com.kirtesh.ecommerce.kafka.payment.NotificationRepository;
import com.kirtesh.ecommerce.kafka.payment.PaymentConfirmation;
import com.kirtesh.ecommerce.notification.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;

import java.time.LocalDateTime;

import static com.kirtesh.ecommerce.notification.NotificationType.ORDER_CONFIRMATION;
import static com.kirtesh.ecommerce.notification.NotificationType.PAYMENT_CONFIRMATION;
import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationsConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;
    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotifications(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info(format("Consuming the message from payment-topic Topic:: %s", paymentConfirmation));
        repository.save(
                Notification.builder()
                        .type(PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );
        var userName = paymentConfirmation.userFirstname() + " " + paymentConfirmation.userLastname();
        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.userEmail(),
              userName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotifications(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(format("Consuming the message from order-topic Topic:: %s", orderConfirmation));
        repository.save(
                Notification.builder()
                        .type(ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );
        var userName = orderConfirmation.user().firstname() + " " + orderConfirmation.user().lastname();
        emailService.sendOrderConfirmationEmail(
                orderConfirmation.user().email(),
                userName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }
}
