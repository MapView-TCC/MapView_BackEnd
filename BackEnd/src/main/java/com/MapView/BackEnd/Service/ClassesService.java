package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.enums.EnumClasses;

import java.time.LocalDate;

public interface ClassesService {

    void getClasse(Long id);
    //void postClasses(EnumClasses course, String name, Users userid, LocalDate creation_date);
    //void update(EnumClasses course, String name, Users userid);
    void getClasses();
}
