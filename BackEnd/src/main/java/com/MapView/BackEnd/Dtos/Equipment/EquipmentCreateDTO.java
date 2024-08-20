package com.MapView.BackEnd.dtos.Equipment;

public record EquipmentCreateDTO(
        String id_equipment,
        String rfid,
        String type,
        String model,
        String admin_rights,
        String observation,
        Long id_location,
        Long id_owner
) {
}
