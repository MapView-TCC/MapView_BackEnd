package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.dtos.Equipment.*;

import com.MapView.BackEnd.serviceImp.EquipmentServiceImp;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.MapView.BackEnd.dtos.Equipment.EquipmentCreateDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipment")
public class EquipmentController {


    private final EquipmentServiceImp equipmentServiceImp;

    public EquipmentController(EquipmentServiceImp equipmentServiceImp) {
        this.equipmentServiceImp = equipmentServiceImp;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<EquipmentDetailsDTO> cadastrar(@RequestBody @Valid EquipmentCreateDTO dados, UriComponentsBuilder uriBuilder){
        var equipment = equipmentServiceImp.createEquipment(dados);

        // boa pratica, para retornar o caminho
        var uri = uriBuilder.path("/api/v1/equipment/{id}").buildAndExpand(equipment.id_equipment()).toUri();
        return ResponseEntity.created(uri).body(new EquipmentDetailsDTO(equipment.id_equipment(), equipment.rfid(), equipment.type(),
                equipment.model(), equipment.validity(), equipment.admin_rights(), equipment.observation(), equipment.id_location(),
                equipment.id_owner(), equipment.operative()));
    }

    @GetMapping
    public ResponseEntity<List<EquipmentDetailsDTO>> getAllEquipment(){
        var list = equipmentServiceImp.getAllEquipment();
        return ResponseEntity.ok(list);
    }

    @GetMapping
    public ResponseEntity<EquipmentDetailsDTO> getById(String id){
        var equipment = equipmentServiceImp.getEquipment(id);
        return ResponseEntity.ok(equipment);
    }

    @PutMapping("/inactivate/{id}")
    @Transactional
    public ResponseEntity<Void> inactivate(@PathVariable String id){
        equipmentServiceImp.inactivateEquipment(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/active/{id}")
    @Transactional
    public ResponseEntity<Void> active(@PathVariable String id){
        equipmentServiceImp.activateEquipment(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<EquipmentDetailsDTO> updateEquipment(@PathVariable String id, @RequestBody EquipmentUpdateDTO dados){
        EquipmentDetailsDTO equipmentDetailsDTO = equipmentServiceImp.updateEquipment(id, dados);
        return ResponseEntity.ok(equipmentDetailsDTO);
    }

}
