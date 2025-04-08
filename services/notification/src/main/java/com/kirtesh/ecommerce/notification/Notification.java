package com.kirtesh.ecommerce.notification;

import com.kirtesh.ecommerce.kafka.order.OrderConfirmation;
import com.kirtesh.ecommerce.kafka.payment.PaymentConfirmation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private NotificationType type;

    private LocalDateTime notificationDate;

    @Transient
    private OrderConfirmation orderConfirmation;

    @Transient
    private PaymentConfirmation paymentConfirmation;

}
