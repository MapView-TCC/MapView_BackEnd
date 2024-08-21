package com.MapView.BackEnd.dtos.Equipment;

import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.MainOwner;

public record EquipmentDetailsDTO(
        String id_equipment,
        String rfid,
        String type,
        String model,
        String validity,
        String admin_rights,
        String observation,
        Location id_location,
        MainOwner id_owner,
        boolean operative) {

    public EquipmentDetailsDTO(Equipment equipment){
        this(equipment.getId_equipment(), equipment.getRfid(), equipment.getType(), equipment.getModel(),
             equipment.getValidity(), equipment.getAdmin_rights(), equipment.getObservation(), equipment.getId_location(),
             equipment.getId_owner(), equipment.isOperative());
    }


}
