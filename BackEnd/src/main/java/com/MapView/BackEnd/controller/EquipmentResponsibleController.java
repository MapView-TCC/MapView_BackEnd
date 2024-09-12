package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.serviceImp.EquipmentResponsibleServiceImp;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleCreateDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleDetailsDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleUpdateDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipmentresponsible")
public class EquipmentResponsibleController {


    private final EquipmentResponsibleServiceImp equipmentResponsibleServiceImp;

    public EquipmentResponsibleController(EquipmentResponsibleServiceImp equipmentResponsibleServiceImp) {
        this.equipmentResponsibleServiceImp = equipmentResponsibleServiceImp;
    }

    @GetMapping
    public ResponseEntity<List<EquipmentResponsibleDetailsDTO>> getAllEquipmentResponsible(){
        var list = equipmentResponsibleServiceImp.getAllEquipmentResponsible();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id_equip_resp}")
    public ResponseEntity<EquipmentResponsibleDetailsDTO> getEquipmentResponsible(@PathVariable Long id_equip_resp){
        EquipmentResponsibleDetailsDTO equipment = equipmentResponsibleServiceImp.getEquipmentResponsible(id_equip_resp);
        return ResponseEntity.ok(equipment);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<EquipmentResponsibleDetailsDTO> createEquipmentResponsible(@RequestBody @Valid EquipmentResponsibleCreateDTO equipmentResponsibleCreateDTO, UriComponentsBuilder uriBuilder){
        var equipmentResponsible = equipmentResponsibleServiceImp.createEquipmentResponsible(equipmentResponsibleCreateDTO);

        // boa pratica, para retornar o caminho
        var uri = uriBuilder.path("/api/v1/equipmentresponsible/{id}").buildAndExpand(equipmentResponsible.id_equip_resp()).toUri();
        return ResponseEntity.created(uri).body(new EquipmentResponsibleDetailsDTO(equipmentResponsible.id_equip_resp(), equipmentResponsible.equipment(), equipmentResponsible.responsible(), equipmentResponsible.start_usage(), equipmentResponsible.end_usage()));
    }

    @PutMapping("/{id_equip_resp}")
    @Transactional
    public ResponseEntity<EquipmentResponsibleDetailsDTO> updateEquipmentResponsible(@PathVariable Long id_equip_resp, @RequestBody @Valid EquipmentResponsibleUpdateDTO dados){
        EquipmentResponsibleDetailsDTO updateEquipmentResponsible = equipmentResponsibleServiceImp.updateEquipmentResponsible(id_equip_resp, dados);
        return ResponseEntity.ok(updateEquipmentResponsible);
    }

    @PutMapping("/inactivate/{id_equip_resp}")
    @Transactional
    public ResponseEntity<Void> inactivate(@PathVariable Long id_equip_resp){
        equipmentResponsibleServiceImp.inactivateEquipmentResponsible(id_equip_resp);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/active/{id_equip_resp}")
    @Transactional
    public ResponseEntity<Void> active(@PathVariable Long id_equip_resp){
        equipmentResponsibleServiceImp.activateEquipmentResponsible(id_equip_resp);
        return ResponseEntity.ok().build();
    }
}


