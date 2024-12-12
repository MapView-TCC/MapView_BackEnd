package com.MapView.BackEnd.dtos.Notification;

import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Notification;

import java.time.LocalDate;

public record NotificationDetailsDTO(Long id_notification, String code, String name_equipment,
                                    String type, String environment_name, String post_name, String validity
) {

    public NotificationDetailsDTO(Notification notification) {
        this(
            notification.getId_notification(),
            notification.getEquipment().getCode(),
            notification.getEquipment().getName_equipment(),
            notification.getEquipment().getType(),
            notification.getEquipment().getLocation().getEnvironment().getEnvironment_name(),
            notification.getEquipment().getLocation().getPost().getPost(),
            getQuarterStringFromDate(notification.getEquipment().getValidity())
        );
    }

    public static String getQuarterStringFromDate(LocalDate date) {
        if(date != null){
            // Obter o ano
            int year = date.getYear();

            // Calcular o trimestre
            int month = date.getMonthValue();
            int quarter = (month - 1) / 3 + 1; // Q1 -> 1, Q2 -> 2, Q3 -> 3, Q4 -> 4

            // Formatar a string no formato "YYYY.QX"
            return year + ".Q" + quarter;
        }
        return null;
    }

}
