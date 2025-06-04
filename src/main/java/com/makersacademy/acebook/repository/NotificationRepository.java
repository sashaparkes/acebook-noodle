package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {

}
