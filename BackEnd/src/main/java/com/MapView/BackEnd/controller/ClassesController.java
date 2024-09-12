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
    public ResponseEntity<ClassesDetaiLDTO> createClasses(@RequestBody @Valid ClassesCreateDTO data, @RequestParam Long userLog_id, UriComponentsBuilder uriBuilder) {
        var classes = classesServiceImp.createClasses(data, userLog_id);
        var uri = uriBuilder.path("/ap1/v1/class/{id}").buildAndExpand(classes.id_classes()).toUri();
        return ResponseEntity.created(uri).body(new ClassesDetaiLDTO(classes.id_classes(), classes.enumCourse(), classes.classes(), classes.user(), classes.criation_date()));

    }

    @GetMapping("/{id_classes}")
    public ResponseEntity<ClassesDetaiLDTO> getClasses(@PathVariable("id_classes") Long id_classes, @RequestParam Long userLog_id) {
        var classe = classesServiceImp.getClasse(id_classes, userLog_id);
        return ResponseEntity.ok(classe);
    }

    @GetMapping
    public ResponseEntity<List<ClassesDetaiLDTO>> getAllClasses(@RequestParam int page, @RequestParam int itens, @RequestParam Long userLog_id) {
        var classes = classesServiceImp.getAllClasses(page, itens, userLog_id);
        return ResponseEntity.ok(classes);
    }

    @PutMapping("/{id_classes}")
    @Transactional
    public ResponseEntity<ClassesDetaiLDTO> updateClass(@PathVariable("id_classes") Long id_classes, @RequestBody @Valid ClassesUpdateDTO data, @RequestParam Long userLog_id){
        var classe = classesServiceImp.updateClasses(id_classes, data, userLog_id);
        return ResponseEntity.ok(classe);
    }



    @PutMapping("/active/{id_classes}")
    @Transactional
    public ResponseEntity<Void> activeClass(@PathVariable("id_classes") Long id_classes, @RequestParam Long userLog_id){
        classesServiceImp.activeClass(id_classes, userLog_id);
        return ResponseEntity.ok().build();

    }
    @PutMapping("/inactive/{id_classes}")
    @Transactional
    public ResponseEntity<Void> inactiveClass(@PathVariable("id_classes") Long id_classes, @RequestParam Long userLog_id){
        classesServiceImp.inactiveClass(id_classes, userLog_id);
        return ResponseEntity.ok().build();

    }



}





