package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
