package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.entities.User;
import com.MapView.BackEnd.enums.EnumClasses;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ClassesService {

    void getClasse(Long id);
    void postClasses(EnumClasses course, String name, User userid, LocalDate creation_date);
    void update(EnumClasses course, String name, User userid);
    void getClasses();
}
