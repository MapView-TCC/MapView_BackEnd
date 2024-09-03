package com.MapView.BackEnd.dtos.Equipment;

import com.MapView.BackEnd.enums.EnumModelEquipment;

public record EquipmentUpdateDTO(
        String id_equipment,
        String name_equipment,
        String rfid,
        String type,
        EnumModelEquipment model,
        String validity,
        String admin_rights,
        String observation,
        Long id_location,
        String id_owner
) {
}
