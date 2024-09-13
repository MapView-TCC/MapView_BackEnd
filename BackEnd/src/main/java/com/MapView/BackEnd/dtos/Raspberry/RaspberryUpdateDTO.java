package com.MapView.BackEnd.dtos.Raspberry;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RaspberryUpdateDTO(
        String raspberry_name,

        Long id_building,
        Long id_area

) {
}
