package com.MapView.BackEnd.dtos.Classes;

import com.MapView.BackEnd.enums.EnumCourse;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ClassesCreateDTO(EnumCourse enumCourse,
                               String classes,
                               Long user_id,
                               LocalDate criation_date) {

}
