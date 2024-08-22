package com.MapView.BackEnd.dtos.Raspberry;

public record RaspberryUpdateDTO(
        String raspberry_name,
        Long id_building,
        Long id_area

) {
}
