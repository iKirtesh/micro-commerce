package com.kirtesh.ecommerce.order;

import com.kirtesh.ecommerce.exception.BusinessException;
import com.kirtesh.ecommerce.kafka.OrderConfirmation;
import com.kirtesh.ecommerce.kafka.OrderProducer;
import com.kirtesh.ecommerce.orderline.OrderLineRequest;
import com.kirtesh.ecommerce.orderline.OrderLineService;
import com.kirtesh.ecommerce.payment.PaymentClient;
import com.kirtesh.ecommerce.payment.PaymentRequest;
import com.kirtesh.ecommerce.product.ProductClient;
import com.kirtesh.ecommerce.product.PurchaseRequest;
import com.kirtesh.ecommerce.user.UserClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserClient userClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final PaymentClient paymentClient;
    private final OrderProducer orderProducer;

    @Transactional
    public Integer createOrder(OrderRequest request) {
        // Check the User --> User microservice --> OpenFeign
        var user = userClient.findUserById(String.valueOf(request.userId()))
                .orElseThrow(() -> new BusinessException("Cannot create order. No user exists with provided id"));
        // Purchase the product --> Product microservice (REST API)
        var purchasedProducts = productClient.purchaseProducts(request.products());
        var order = this.repository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            OrderLineRequest orderLineRequest = new OrderLineRequest(
                    null,
                    order.getId(),
                    purchaseRequest.productId(),
                    purchaseRequest.quantity()
            );

            orderLineService.saveOrderLine(orderLineRequest);
        }
        var paymentRequest = new PaymentRequest(
                request.totalAmount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                user
        );
        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.totalAmount(),
                        request.paymentMethod(),
                        user,
                        purchasedProducts
                )
        );

        return order.getId();
    }


    public List<OrderResponse> findAllOrders() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }
}
