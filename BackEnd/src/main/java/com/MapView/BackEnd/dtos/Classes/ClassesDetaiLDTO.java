package com.MapView.BackEnd.dtos.Classes;

import com.MapView.BackEnd.entities.Classes;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.EnumCourse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public record ClassesDetaiLDTO (
                                Long id_classes,
                                EnumCourse enumCourse,
                                String classes,
                                Users user,
                                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
                                LocalDate creation_date) {

    public ClassesDetaiLDTO(Classes classes){
        this(classes.getId_classes(),classes.getEnumCourse(), classes.getClasses(), classes.getUser(),classes.getCreation_date());
    }
}
