package com.MapView.BackEnd.dtos.TrackingHistory;

import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleDetailsDTO;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.EquipmentResponsible;
import com.MapView.BackEnd.entities.Responsible;
import com.MapView.BackEnd.entities.TrackingHistory;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record TrackingHistoryWrongLocationDTO(


        Responsible responsible



) {
    public TrackingHistoryWrongLocationDTO(EquipmentResponsible equipmentResponsible){
        this(equipmentResponsible.getId_responsible());
    }
}

