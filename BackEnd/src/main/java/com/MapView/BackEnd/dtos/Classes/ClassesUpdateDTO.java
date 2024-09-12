package com.MapView.BackEnd.dtos.Classes;

import com.MapView.BackEnd.enums.EnumCourse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ClassesUpdateDTO(EnumCourse enumCourse,
                               @NotBlank(message = "Classes cannot be blank")
                               String classes,
                               @Min(1)
                               @Positive
                               Long user_id,
                               LocalDateTime criation_date) {
}
