package com.MapView.BackEnd.Controller;

import com.MapView.BackEnd.dtos.Equipment.*;

import com.MapView.BackEnd.ServiceImp.EquipmentServiceImp;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.MapView.BackEnd.dtos.Equipment.EquipmentCreateDTO;

@RestController
@RequestMapping("/api/v1/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentServiceImp equipmentServiceImp;

    @PostMapping
    @Transactional
    public ResponseEntity<EquipmentCreateDTO> cadastrar(@RequestBody @Valid EquipmentCreateDTO dados, UriComponentsBuilder uriBuilder){
        equipmentServiceImp.createEquipment(dados);

        // boa pratica, para retornar o caminho
        var uri = uriBuilder.path("/api/v1/equipment/{id}").buildAndExpand(dados.id_equipment()).toUri();
        return ResponseEntity.created(uri).body(new EquipmentCreateDTO(dados.id_equipment(), dados.rfid(), dados.type(), dados.model(), dados.admin_rights(), dados.observation(), dados.id_location(), dados.id_owner()));
    }
}
