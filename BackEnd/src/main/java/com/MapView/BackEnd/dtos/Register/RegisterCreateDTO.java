package com.MapView.BackEnd.dtos.Register;

import com.MapView.BackEnd.enums.EnumModelEquipment;
import jakarta.validation.constraints.Min;

import java.util.List;

public record RegisterCreateDTO (
        //Equipment

        @Min(value = 8, message = "Id_equipment must be under than 8")
        String id_equipment,

        String name_equipment,
        Long rfid,

        String type,
        EnumModelEquipment model,
        String validity,
        String admin_rights,
        String observation,
        //location Equipment
        Long id_building,
        Long id_environment,
        String post,
        String id_owner,
        String costCenter_name,
        List<ResponsibleResgisterDTO> dataResponsible

) {
}
