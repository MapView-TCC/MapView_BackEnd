package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.dtos.Area.AreaDetailsDTO;
import com.MapView.BackEnd.serviceImp.ResponsibleServiceImp;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleCrateDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleUpdateDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
    public ResponseEntity<ResponsibleDetailsDTO> createResponsible(@RequestBody @Valid ResponsibleCrateDTO data, UriComponentsBuilder uriBuilder, @RequestParam Long user_id){
        var responsible  = responsibleServiceImp.createResposible(data, user_id);
        var uri = uriBuilder.path("/ap1/v1/responsible/{id}").buildAndExpand(responsible.responsible_id()).toUri();
        return ResponseEntity.created(uri).body(new ResponsibleDetailsDTO(responsible.responsible_id(), responsible.responsible_name(), responsible.edv(), responsible.classes(),responsible.users(),responsible.operative()));
    }
    @PostMapping("/{responsible_id}")
    @Transactional
    public ResponseEntity<ResponsibleDetailsDTO> updateResponsible(@PathVariable("responsible_id") Long responsible_id, @RequestBody ResponsibleUpdateDTO data, @RequestParam Long user_id){
        var responsible = responsibleServiceImp.updateResposible(responsible_id,data, user_id);
        return ResponseEntity.ok(responsible);

    }
    @GetMapping("/{responsible_id}")
    public ResponseEntity<ResponsibleDetailsDTO> getReponsible(@PathVariable("responsible_id") Long responsible_id, @RequestParam Long user_id){
        var user = responsibleServiceImp.getResposible(responsible_id, user_id);
        return ResponseEntity.ok(user);

    }
    @GetMapping
    public ResponseEntity<List<ResponsibleDetailsDTO>> getAllReponsible(@RequestParam int page, @RequestParam int itens, @RequestParam Long user_id){
        var user = responsibleServiceImp.getAllResposible(page, itens, user_id);
        return ResponseEntity.ok(user);

    }

    @PutMapping("/inactivate/{responsible_id}")
    @Transactional
    public ResponseEntity<AreaDetailsDTO> inactivate(@PathVariable Long responsible_id, @RequestParam Long user_id){
        responsibleServiceImp.inactivateResposible(responsible_id,user_id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/active/{responsible_id}")
    @Transactional
    public ResponseEntity<AreaDetailsDTO> active( @PathVariable Long responsible_id,@RequestParam Long user_id){
        responsibleServiceImp.activeResposible(responsible_id,user_id);
        return ResponseEntity.ok().build();
    }
}
