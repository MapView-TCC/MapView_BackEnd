package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.dtos.Classes.ClassesCreateDTO;
import com.MapView.BackEnd.dtos.Classes.ClassesDetaiLDTO;
import com.MapView.BackEnd.dtos.Classes.ClassesUpdateDTO;

import java.util.List;

public interface ClassesService {

    ClassesDetaiLDTO getClasse(Long id);
    ClassesDetaiLDTO createClasses(ClassesCreateDTO data);
    ClassesDetaiLDTO updateClasses(Long classes_id, ClassesUpdateDTO data);
    List<ClassesDetaiLDTO> getAllClasses();
    void activeClass(Long class_id);
    void inactiveClass(Long class_id);
}
