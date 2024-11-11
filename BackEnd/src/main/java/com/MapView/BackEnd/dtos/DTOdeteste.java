package com.MapView.BackEnd.dtos;

import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.EquipmentResponsible;
import com.MapView.BackEnd.entities.Responsible;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record DTOdeteste(Long id_equip_resp,
                         Equipment equipment,
                         Responsible responsible,
                         @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
                         LocalDate start_usage,
                         @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
                         LocalDate end_usage) {
    public DTOdeteste(EquipmentResponsible equipmentResponsible) {
        this(equipmentResponsible.getId_equip_resp(), equipmentResponsible.getEquipment(),
                equipmentResponsible.getResponsible(), equipmentResponsible.getStart_usage(),
                equipmentResponsible.getEnd_usage());
    }
}
