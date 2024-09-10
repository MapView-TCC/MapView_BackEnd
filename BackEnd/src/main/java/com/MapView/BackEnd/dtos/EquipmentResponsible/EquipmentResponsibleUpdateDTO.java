package com.MapView.BackEnd.dtos.EquipmentResponsible;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record EquipmentResponsibleUpdateDTO (
        String id_equipment,
        Long id_responsible,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate start_usage,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate end_usage

) {
}
