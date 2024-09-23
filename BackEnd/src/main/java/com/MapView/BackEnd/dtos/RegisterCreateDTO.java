package com.MapView.BackEnd.dtos;

import com.MapView.BackEnd.enums.EnumModelEquipment;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RegisterCreateDTO (
        @NotBlank(message = "Equipment id cannot be blank")
        @Size(min = 8,message = "Equipment_id must be size 8")
        String id_equipment,
        @NotBlank(message = "Equipment name cannot be blank")
        String name_equipment,
        Long rfid,
        @NotBlank(message = "Type cannot be blank")
        String type,
        @NotBlank(message = "Type cannot be blank")
        EnumModelEquipment model,
        String validity,
        String admin_rights,

        String observation,
        @Positive
        @Min(1)
        Long id_location,
        @NotBlank
        String id_owner,
        @Positive
        @Min(1)
                Long id_post,
        @Positive
        @Min(1)
        Long id_eviroment,
        @NotBlank(message = "Responsible name name id cannot be blank.")
        String responsible_name,
        @NotBlank(message = "Edv id cannot be blank.")
        String edv,
        @Min(value = 1, message = "Building Id cannot be smaller than 0.")
        @Positive(message = "Classes Id must be Positive.")
        Long id_classes,
        @Min(value = 1, message = "Building Id cannot be smaller than 0.")
        @Positive(message = "User Id must be Positive.")
        Long id_user
) {
}
