package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Classes.ClassesCreateDTO;
import com.MapView.BackEnd.dtos.Classes.ClassesDetaiLDTO;
import com.MapView.BackEnd.dtos.Classes.ClassesUpdateDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ClassesService {

    ClassesDetaiLDTO getClasse(Long id, Long user_id);
    ClassesDetaiLDTO createClasses(ClassesCreateDTO data, Long user_id);
    ClassesDetaiLDTO updateClasses(Long classes_id, ClassesUpdateDTO data, Long user_id);
    List<ClassesDetaiLDTO> getAllClasses(int page, int itens, Long user_id);
    void activeClass(Long class_id, Long user_id);
    void inactiveClass(Long class_id, Long user_id);
}
