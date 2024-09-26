package com.MapView.BackEnd.dtos.TrackingHistory;

import com.MapView.BackEnd.entities.Equipment;

import java.util.List;

public record TrackingHistoryWrongLocationDTO(Equipment equipment, List<String> equipmentResponsible) {

    public TrackingHistoryWrongLocationDTO(Equipment equipment, List<String> equipmentResponsible) {
        this.equipment = equipment; // O objeto Equipment completo
        this.equipmentResponsible = equipmentResponsible; // Nome do responsável
    }
}
