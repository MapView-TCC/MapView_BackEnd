package com.MapView.BackEnd.dtos.Equipment;

import com.MapView.BackEnd.enums.EnumModelEquipment;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EquipmentUpdateDTO(
        @NotBlank(message = "Equipment id cannot be blank") @Size(min = 9)
        String id_equipment,
        @NotBlank(message = "Equipment name cannot be blank")
        String name_equipment,
        @Positive
        @Size(min = 15)
        int rfid,
        @NotBlank(message = "Type cannot be blank")
        String type,
        EnumModelEquipment model,
        @NotBlank(message = "Validity cannot be blank")
        String validity,
        @NotBlank(message = "Admin cannot be blank")
        String admin_rights,
        @NotBlank(message = "Observation cannot be blank")
        @Size(min = 5,message = "Observation must be under than 0")
        String observation,
        @Positive
        @Min(1)
        Long id_location,
        @Positive
        @Min(1)
        String id_owner
) {


}
