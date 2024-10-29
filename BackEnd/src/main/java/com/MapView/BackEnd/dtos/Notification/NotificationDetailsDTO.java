package com.MapView.BackEnd.dtos.Notification;

import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Notification;

public record NotificationDetailsDTO(Long id_notification, String code, String name_equipment,
                                    String type, String environment_name, String post_name
) {

    public NotificationDetailsDTO(Notification notification) {
        this(
            notification.getId_notification(),
            notification.getEquipment().getCode(),
            notification.getEquipment().getName_equipment(),
            notification.getEquipment().getType(),
            notification.getEquipment().getLocation().getEnvironment().getEnvironment_name(),
            notification.getEquipment().getLocation().getPost().getPost());
    }
}
