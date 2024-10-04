package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Notification.NotificationCreateDTO;
import com.MapView.BackEnd.dtos.Notification.NotificationDetailsDTO;
import com.MapView.BackEnd.entities.Notification;

import java.util.List;

public interface NotificationService {

    List<NotificationDetailsDTO> getAllNotification();
    void deleteNotificationById(Long id_notification);
    NotificationDetailsDTO getNotification(Long id_notification);
    NotificationDetailsDTO createNotification(NotificationCreateDTO data);

}
