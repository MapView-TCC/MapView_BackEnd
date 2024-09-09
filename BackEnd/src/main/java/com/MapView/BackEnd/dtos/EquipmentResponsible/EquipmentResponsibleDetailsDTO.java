package com.MapView.BackEnd.dtos.EquipmentResponsible;

import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.EquipmentResponsible;
import com.MapView.BackEnd.entities.Responsible;

import java.time.LocalDate;

public record EquipmentResponsibleDetailsDTO(
        Long id_equip_resp,
        Equipment equipment,
        Responsible responsible,
        LocalDate start_usage,
        LocalDate end_usage
) {
    public EquipmentResponsibleDetailsDTO(EquipmentResponsible equipmentResponsible){
        this(equipmentResponsible.getId_equip_resp(), equipmentResponsible.getId_equipment(),
                equipmentResponsible.getId_responsible(), equipmentResponsible.getStart_usage(),
                equipmentResponsible.getEnd_usage());
    }
}
