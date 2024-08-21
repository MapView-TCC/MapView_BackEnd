package com.MapView.BackEnd.ServiceImp;

import com.MapView.BackEnd.Repository.ClassesRepository;
import com.MapView.BackEnd.Repository.UserRepository;
import com.MapView.BackEnd.Service.ClassesService;
import com.MapView.BackEnd.dtos.Classes.ClassesCreateDTO;
import com.MapView.BackEnd.dtos.Classes.ClassesDetaiLDTO;
import com.MapView.BackEnd.dtos.Classes.ClassesUpdateDTO;
import com.MapView.BackEnd.entities.Classes;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class ClassesServiceImp implements ClassesService {

    private final ClassesRepository classesRepository;
    private final UserRepository userRepository;

    public ClassesServiceImp(ClassesRepository classesRepository, UserRepository userRepository) {
        this.classesRepository = classesRepository;
        this.userRepository = userRepository;

    }
    @Override
    public ClassesDetaiLDTO createClasses(ClassesCreateDTO data) {
        var user = userRepository.findById(data.user_id()).orElseThrow(() -> new NotFoundException("User Id Not Found"));
        var classe = new Classes(data,user);
        classesRepository.save(classe);

        return new ClassesDetaiLDTO(classe);
    }

    @Override
    public ClassesDetaiLDTO getClasse(Long id) {
        var classe = classesRepository.findById(id).orElseThrow(() -> new NotFoundException("User Id Not Found"));
        return new ClassesDetaiLDTO(classe);
    }

    @Override
    public ClassesDetaiLDTO updateClasses(Long classes_id ,ClassesUpdateDTO data) {
        var classes = classesRepository.findById(data.user_id()).orElseThrow(() -> new NotFoundException("Class Id Not Found"));
        if(data.user_id() != null){
            var user = userRepository.findById(data.user_id()).orElseThrow(() -> new NotFoundException("User Id Not Found"));
            classes.setId_user(user);
        }
        if(data.enumCourse() != null){
            classes.setEnumCourse(data.enumCourse());
        }
        if(data.criation_date() != null){
            classes.setCreation_date(data.criation_date());
        }
        if(data.classes() != null){
            classes.setClasses(data.classes());
        }
        classesRepository.save(classes);

        return new ClassesDetaiLDTO(classes);
    }

    @Override
    public List<ClassesDetaiLDTO> getAllClasses() {
        return this.classesRepository.findClassesByOperativeTrue().stream().map(ClassesDetaiLDTO::new).toList();
    }

    @Override
    public void activeClass(Long class_id) {
        var classe = classesRepository.findById(class_id).orElseThrow(()-> new NotFoundException("Class Id Not Found"));
        classe.setOperative(true);
        classesRepository.save(classe);
    }

    @Override
    public void inactiveClass(Long class_id) {
        var classe = classesRepository.findById(class_id).orElseThrow(()-> new NotFoundException("Class Id Not Found"));
        classe.setOperative(false);
        classesRepository.save(classe);

    }


}
