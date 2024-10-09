package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.dtos.Notification.NotificationCreateDTO;
import com.MapView.BackEnd.dtos.Notification.NotificationDetailsDTO;
import com.MapView.BackEnd.entities.Notification;
import com.MapView.BackEnd.serviceImp.NotificationServiceImp;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
@Tag(name = "Notification", description = "Operations related to notifications management")
public class NotificationController {

    private final NotificationServiceImp notificationServiceImp;

    public NotificationController(NotificationServiceImp notificationServiceImp) {
        this.notificationServiceImp = notificationServiceImp;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<NotificationDetailsDTO> createNotification(@RequestBody NotificationCreateDTO data, UriComponentsBuilder builder){
        var notification = notificationServiceImp.createNotification(data);
        var uri = builder.path("/api/v1/notification/{id}").buildAndExpand(notification.id_notification()).toUri();
        return ResponseEntity.created(uri).body(new NotificationDetailsDTO(notification.id_notification(), notification.id_equipment(), notification.name_equipment(),
                notification.type(), notification.environment_name(), notification.post_name()));
    }

    @GetMapping("/{id_notification}")
    public ResponseEntity<NotificationDetailsDTO> getIdNotifications(@PathVariable("id_notification") Long id_notification){
        var notification = notificationServiceImp.getNotification(id_notification);
        return ResponseEntity.ok(notification);
    }

    @GetMapping
    public ResponseEntity<List<NotificationDetailsDTO>> getAllNotifications(){
        var list = notificationServiceImp.getAllNotification();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id_notification}")
    public ResponseEntity<Void> deleteNotification(@PathVariable("id_notification") Long id_notification){
        notificationServiceImp.deleteNotificationById(id_notification);
        return ResponseEntity.ok().build();
    }

}
