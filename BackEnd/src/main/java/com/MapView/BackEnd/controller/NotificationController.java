package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.dtos.Notification.NotificationCreateDTO;
import com.MapView.BackEnd.dtos.Notification.NotificationDetailsDTO;
import com.MapView.BackEnd.entities.Notification;
import com.MapView.BackEnd.serviceImp.NotificationServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a new notification", description = "Endpoint to create a new notification record in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notification record successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<NotificationDetailsDTO> createNotification(@RequestBody NotificationCreateDTO data, UriComponentsBuilder builder){
        var notification = notificationServiceImp.createNotification(data);
        var uri = builder.path("/api/v1/notification/{id}").buildAndExpand(notification.id_notification()).toUri();
        return ResponseEntity.created(uri).body(new NotificationDetailsDTO(notification.id_notification(), notification.code(), notification.name_equipment(),
                notification.type(), notification.environment_name(), notification.post_name()));
    }

    @Operation(summary = "Get notification by ID", description = "Retrieve notification details by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    @GetMapping("/{id_notification}")
    public ResponseEntity<NotificationDetailsDTO> getIdNotifications(@PathVariable("id_notification") Long id_notification){
        var notification = notificationServiceImp.getNotification(id_notification);
        return ResponseEntity.ok(notification);
    }

    @Operation(summary = "Get all notification", description = "Retrieve a of all notification records.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification records retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<NotificationDetailsDTO>> getAllNotifications(){
        var list = notificationServiceImp.getAllNotification();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Permanently delete notification", description = "Deleting notification records.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification successfully deleted")
    })
    @DeleteMapping("/{id_notification}")
    public ResponseEntity<Void> deleteNotification(@PathVariable("id_notification") Long id_notification){
        notificationServiceImp.deleteNotificationById(id_notification);
        return ResponseEntity.ok().build();
    }

}
