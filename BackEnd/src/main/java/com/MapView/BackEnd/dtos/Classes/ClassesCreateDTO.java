package com.MapView.BackEnd.dtos.Classes;

import com.MapView.BackEnd.enums.EnumCourse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ClassesCreateDTO(@NotBlank
                               EnumCourse enumCourse,
                               @NotBlank(message = "Classes cannot be blank")
                               String classes,
                               @Min(1)
                               @Positive(message = "User id must be Positive")
                               Long user_id,
                               LocalDate criation_date) {

}
