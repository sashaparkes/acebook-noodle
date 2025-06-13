package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Notification;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
    List<Notification> findAllByReceivingUserId(Long receivingUserId);
    List<Notification> findByReceivingUserIdOrderByCreatedAtDesc(Long receivingUserId);
    Integer countByReceivingUserIdAndIsRead(Long receivingUserId, Boolean isRead);
}
