package com.MapView.BackEnd.dtos.Raspberry;

public record RaspberryCreateDTO(
        String raspberry_name,
        Long id_building,
        Long id_area

        ) {
}
