package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.serviceImp.ClassesServiceImp;
import com.MapView.BackEnd.dtos.Classes.ClassesCreateDTO;
import com.MapView.BackEnd.dtos.Classes.ClassesDetaiLDTO;
import com.MapView.BackEnd.dtos.Classes.ClassesUpdateDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/ap1/v1/class")

public class ClassesController {

    private final ClassesServiceImp classesServiceImp;

    public ClassesController(ClassesServiceImp classesServiceImp) {
        this.classesServiceImp = classesServiceImp;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<ClassesDetaiLDTO> createClasses(@RequestBody @Valid ClassesCreateDTO data, @RequestParam Long user_id, UriComponentsBuilder uriBuilder) {
        var classes = classesServiceImp.createClasses(data, user_id);
        var uri = uriBuilder.path("/ap1/v1/class/{id}").buildAndExpand(classes.id_classes()).toUri();
        return ResponseEntity.created(uri).body(new ClassesDetaiLDTO(classes.id_classes(), classes.enumCourse(), classes.classes(), classes.user(), classes.criation_date()));

    }

    @GetMapping("/{classes_id}")
    public ResponseEntity<ClassesDetaiLDTO> getClasses(@PathVariable("classes_id") Long classes_id, @RequestParam Long user_id) {
        var classe = classesServiceImp.getClasse(classes_id, user_id);
        return ResponseEntity.ok(classe);
    }

    @GetMapping
    public ResponseEntity<List<ClassesDetaiLDTO>> getAllClasses(@RequestParam int page, @RequestParam int itens, @RequestParam Long user_id) {
        var classes = classesServiceImp.getAllClasses(page, itens, user_id);
        return ResponseEntity.ok(classes);
    }
    @PostMapping("/{class_id}")
    @Transactional
    public ResponseEntity<ClassesDetaiLDTO> updateClass(@PathVariable("class_id") Long class_id, @RequestBody ClassesUpdateDTO data, @RequestParam Long user_id){
        var classe = classesServiceImp.updateClasses(class_id, data, user_id);
        return ResponseEntity.ok(classe);
    }



    @PutMapping("/active/{class_id}")
    @Transactional
    public ResponseEntity<Void> activeClass(@PathVariable("class_id") Long class_id, @RequestParam Long user_id){
        classesServiceImp.activeClass(class_id, user_id);
        return ResponseEntity.ok().build();

    }
    @PutMapping("/inactive/{class_id}")
    @Transactional
    public ResponseEntity<Void> inactiveClass(@PathVariable("class_id") Long class_id, @RequestParam Long user_id){
        classesServiceImp.inactiveClass(class_id, user_id);
        return ResponseEntity.ok().build();

    }



}





