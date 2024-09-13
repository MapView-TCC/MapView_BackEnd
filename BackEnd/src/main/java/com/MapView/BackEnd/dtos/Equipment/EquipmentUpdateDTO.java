package com.MapView.BackEnd.dtos.Equipment;

import com.MapView.BackEnd.enums.EnumModelEquipment;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EquipmentUpdateDTO(
        String id_equipment,
        String name_equipment,
        Long rfid,
        String type,
        EnumModelEquipment model,
        String validity,
        String admin_rights,
        String observation,
        Long id_location,
        String id_owner // id owner é um string
) {


}
