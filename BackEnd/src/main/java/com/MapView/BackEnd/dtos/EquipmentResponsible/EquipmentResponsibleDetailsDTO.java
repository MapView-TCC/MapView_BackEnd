package com.MapView.BackEnd.dtos.EquipmentResponsible;

import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.enums.EnumModelEquipment;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record EquipmentResponsibleDetailsDTO(
        Long id_equip_resp,
        Equipment equipment,
        Responsible responsible,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate start_usage,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate end_usage
) {
    public EquipmentResponsibleDetailsDTO(EquipmentResponsible equipmentResponsible){
        this(equipmentResponsible.getId_equip_resp(), equipmentResponsible.getIdEquipment(),
                equipmentResponsible.getId_responsible(), equipmentResponsible.getStart_usage(),
                equipmentResponsible.getEnd_usage());
    }
}
