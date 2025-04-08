package com.kirtesh.ecommerce.kafka.payment;

import com.kirtesh.ecommerce.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, String> {
}