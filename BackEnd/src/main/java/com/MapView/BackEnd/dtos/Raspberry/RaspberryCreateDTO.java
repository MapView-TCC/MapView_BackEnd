package com.MapView.BackEnd.dtos.Raspberry;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RaspberryCreateDTO(
        @NotBlank(message = "Raspberry name id cannot be blank.")
        @NotNull(message = "Raspberry name id cannot be blank.")
        String id_raspberry,
        @Min(value = 1, message = "Building Id cannot be smaller than 0.")
        @Positive(message = "CostCenter Id must be Positive.")
        Long building,
        @Min(value = 1, message = "Area Id cannot be smaller than 0.")
        @Positive(message = "CostCenter Id must be Positive.")
        Long area

        ) {
}
