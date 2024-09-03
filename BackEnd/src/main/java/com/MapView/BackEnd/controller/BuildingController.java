package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.serviceImp.BuildingServiceImp;
import com.MapView.BackEnd.dtos.Building.BuildingCreateDTO;
import com.MapView.BackEnd.dtos.Building.BuildingDetailsDTO;
import com.MapView.BackEnd.dtos.Building.BuildingUpdateDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bulding")
public class BuildingController {


    private final BuildingServiceImp buildingServiceImp;

    public BuildingController(BuildingServiceImp buildingServiceImp) {
        this.buildingServiceImp = buildingServiceImp;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<BuildingDetailsDTO> createBuilding(@RequestBody @Valid BuildingCreateDTO dados,@RequestParam Long user_id, UriComponentsBuilder uriBuilder){
        var building = buildingServiceImp.createBuilding(dados,user_id);


        var uri = uriBuilder.path("/api/v1/bulding/{id}").buildAndExpand(building.id_building()).toUri();
        return ResponseEntity.created(uri).body(new BuildingDetailsDTO(building.id_building(), building.building_code(), building.operative()));
    }

    @GetMapping
    public ResponseEntity<List<BuildingDetailsDTO>> getAllBuilding(@RequestParam int page, @RequestParam int itens,@RequestParam Long user_id){
        var list = buildingServiceImp.getAllBuilding(page, itens,user_id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("{id_building}")
    public ResponseEntity<BuildingDetailsDTO> getBuilding(@PathVariable Long id_building,@RequestParam Long user_id){
        var building = buildingServiceImp.getBuilding(id_building,user_id);
        return ResponseEntity.ok(building);
    }

    @PostMapping("{id_building}")
    @Transactional
    public ResponseEntity<BuildingDetailsDTO> updateBuilding(@PathVariable Long id_building,@RequestBody BuildingUpdateDTO dados,@RequestParam Long user_id){
        BuildingDetailsDTO updateBuilding = buildingServiceImp.updateBuilding(id_building, dados, user_id);
        return ResponseEntity.ok(updateBuilding);
    }

    @PutMapping("/inactivate/{id_building}")
    @Transactional
    public ResponseEntity<Void> inactivate(@PathVariable Long id_building,@RequestParam Long user_id){
        buildingServiceImp.inactivateBuilding(id_building,user_id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/active/{id_building}")
    @Transactional
    public ResponseEntity<Void> active(@PathVariable Long id_building,@RequestParam Long user_id){
        buildingServiceImp.activateBuilding(id_building,user_id);
        return ResponseEntity.ok().build();
    }
}
