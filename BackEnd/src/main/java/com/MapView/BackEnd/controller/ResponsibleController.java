package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.serviceImp.ResponsibleServiceImp;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleCrateDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleUpdateDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/ap1/v1/responsible")
public class ResponsibleController {


    private final ResponsibleServiceImp responsibleServiceImp;

    public ResponsibleController(ResponsibleServiceImp responsibleServiceImp) {
        this.responsibleServiceImp = responsibleServiceImp;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<ResponsibleDetailsDTO> createResponsible(ResponsibleCrateDTO data, UriComponentsBuilder uriBuilder){
        var responsible  = responsibleServiceImp.createResposible(data);
        var uri = uriBuilder.path("/ap1/v1/responsible/{id}").buildAndExpand(responsible.responsible_id()).toUri();
        return ResponseEntity.created(uri).body(new ResponsibleDetailsDTO(responsible.responsible_id(), responsible.responsible_name(), responsible.edv(), responsible.classes(),responsible.users(),responsible.operative()));
    }
    @PostMapping("/{responsible_id}")
    @Transactional
    public ResponseEntity<ResponsibleDetailsDTO> updateResponsible(@PathVariable("responsible_id") Long responsible_id, @RequestBody ResponsibleUpdateDTO data){
        var responsible = responsibleServiceImp.updateResposible(responsible_id,data);
        return ResponseEntity.ok(responsible);

    }
    @GetMapping("/{user_id}")
    public ResponseEntity<ResponsibleDetailsDTO> getReponsible(@PathVariable("user_id") Long user_id){
        var user = responsibleServiceImp.getResposible(user_id);
        return ResponseEntity.ok(user);

    }
    @GetMapping()
    public ResponseEntity<List<ResponsibleDetailsDTO>> getAllReponsible(@PathVariable("user_id") Long user_id){
        var user = responsibleServiceImp.getAllResposible();
        return ResponseEntity.ok(user);

    }
}
