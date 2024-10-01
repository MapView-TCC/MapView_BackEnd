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
        Long id_equip_resp,
        Equipment equipment,
        Responsible responsible,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate start_usage,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate end_usage

) {
    public TrackingHistoryWrongLocationDTO(EquipmentResponsible equipmentResponsible){
        this(equipmentResponsible.getId_equip_resp(), equipmentResponsible.getIdEquipment(),
                equipmentResponsible.getId_responsible(), equipmentResponsible.getStart_usage(),
                equipmentResponsible.getEnd_usage());
    }
}

