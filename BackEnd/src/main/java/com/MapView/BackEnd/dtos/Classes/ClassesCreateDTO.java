package com.MapView.BackEnd.dtos.Classes;

import com.MapView.BackEnd.enums.EnumCourse;

import java.time.LocalDateTime;

public record ClassesCreateDTO(EnumCourse enumCourse, String classes, Long user_id, LocalDateTime criation_date) {
}
