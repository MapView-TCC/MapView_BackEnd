package com.MapView.BackEnd.dtos.Equipment;

import com.MapView.BackEnd.dtos.Notification.NotificationDetailsDTO;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.MainOwner;
import com.MapView.BackEnd.enums.EnumModelEquipment;
import jakarta.validation.Valid;

import java.time.LocalDate;

public record EquipmentDetailsDTO(
        Long id_equipment,
        String code,
        String name_equipment,
        long rfid,
        String type,
        EnumModelEquipment model,
        String validity,
        String admin_rights,
        String observation,
        Location location,
        MainOwner owner) {

    public EquipmentDetailsDTO(Equipment equipment) {
        this(equipment.getId_equipment(), equipment.getCode(), equipment.getName_equipment(), equipment.getRfid(), equipment.getType(), equipment.getModel(), getQuarterStringFromDate(equipment.getValidity()), equipment.getAdmin_rights(), equipment.getObservation(), equipment.getLocation(),
                equipment.getOwner());
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
