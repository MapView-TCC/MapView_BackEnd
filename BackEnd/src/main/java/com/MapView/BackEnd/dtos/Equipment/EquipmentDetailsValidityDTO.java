package com.MapView.BackEnd.dtos.Equipment;

import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.MainOwner;
import com.MapView.BackEnd.enums.EnumModelEquipment;

public record EquipmentDetailsValidityDTO(
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

    public EquipmentDetailsValidityDTO(Equipment equipment,String validity_tranformated) {
            this(equipment.getId_equipment(), equipment.getCode(), equipment.getName_equipment(), equipment.getRfid(), equipment.getType(), equipment.getModel(), validity_tranformated, equipment.getAdmin_rights(), equipment.getObservation(), equipment.getLocation(),
                    equipment.getOwner());
        }
}
