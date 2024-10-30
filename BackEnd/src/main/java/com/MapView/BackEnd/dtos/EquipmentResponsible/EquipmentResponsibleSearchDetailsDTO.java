package com.MapView.BackEnd.dtos.EquipmentResponsible;

import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.MainOwner;
import com.MapView.BackEnd.enums.EnumModelEquipment;

import java.time.LocalDate;
import java.util.List;

public record EquipmentResponsibleSearchDetailsDTO (
        String code,
        String name_equipment,
        long rfid,
        String type,
        EnumModelEquipment model,
        String validity,
        String admin_rights,
        String observation,
        Location location,
        MainOwner owner,
        List<ResponsibleDetailsDTO> responsibles
) {
    public EquipmentResponsibleSearchDetailsDTO(EquipmentDetailsDTO equipment, List<ResponsibleDetailsDTO> responsible){
        this(equipment.code(), equipment.name_equipment(), equipment.rfid(), equipment.type(), equipment.model(),
                equipment.validity(), equipment.admin_rights(), equipment.observation(), equipment.location(),
                equipment.owner(),responsible);
    }
}