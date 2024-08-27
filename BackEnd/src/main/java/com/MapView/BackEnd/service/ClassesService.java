package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Classes.ClassesCreateDTO;
import com.MapView.BackEnd.dtos.Classes.ClassesDetaiLDTO;
import com.MapView.BackEnd.dtos.Classes.ClassesUpdateDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ClassesService {

    ClassesDetaiLDTO getClasse(Long id);
    ClassesDetaiLDTO createClasses(ClassesCreateDTO data);
    ClassesDetaiLDTO updateClasses(Long classes_id, ClassesUpdateDTO data);
    List<ClassesDetaiLDTO> getAllClasses(int page, int itens);
    void activeClass(Long class_id);
    void inactiveClass(Long class_id);
}
