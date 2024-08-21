package com.MapView.BackEnd.dtos.Classes;

import com.MapView.BackEnd.entities.Classes;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.EnumCourse;

import java.time.LocalDateTime;

public record ClassesDetaiLDTO (Long id_classes, EnumCourse enumCourse, String classes, Users user, LocalDateTime criation_date) {
    public ClassesDetaiLDTO(Classes classes){
        this(classes.getId_classes(),classes.getEnumCourse(), classes.getClasses(), classes.getId_user(),classes.getCreation_date());
    }
}
