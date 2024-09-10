package com.MapView.BackEnd.dtos.EquipmentResponsible;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record EquipmentResponsibleCreateDTO(
        @NotBlank(message = "Equipment id cannot be blank")
        String id_equipment,
        @NotBlank(message = "Responsible id cannot be blank")
        Long id_responsible,
        LocalDate start_usage,
        LocalDate end_usage

) {

}
