package com.MapView.BackEnd.dtos.EquipmentResponsible;

import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Responsible;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EquipmentResponsibleCreateDTO(
        @NotNull(message = "Equipment id cannot be null")
        Long equipment,
        @NotNull(message = "Responsible id cannot be null")
        Long responsible,
        LocalDate start_usage,
        LocalDate end_usage

) {

}
