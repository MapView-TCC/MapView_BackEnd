package com.MapView.BackEnd.dtos.Equipment;

public record EquipmentUpdateDTO(
        String id_equipment,
        String name_equipment,
        String rfid,
        String type,
        String model,
        String validity,
        String admin_rights,
        String observation,
        Long id_location,
        String id_owner
) {
}
