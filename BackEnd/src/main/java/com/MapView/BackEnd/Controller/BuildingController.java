package com.MapView.BackEnd.Controller;

import com.MapView.BackEnd.ServiceImp.BuildingServiceImp;
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
    public ResponseEntity<BuildingDetailsDTO> create(@RequestBody @Valid BuildingCreateDTO dados, UriComponentsBuilder uriBuilder){
        var building = buildingServiceImp.createBuilding(dados);

        var uri = uriBuilder.path("/api/v1/bulding/{id}").buildAndExpand(building.id_building()).toUri();
        return ResponseEntity.created(uri).body(new BuildingDetailsDTO(building.id_building(), building.building_code(), building.operative()));
    }

    @GetMapping
    public ResponseEntity<List<BuildingDetailsDTO>> listBuilding(){
        var list = buildingServiceImp.getAllBuilding();
        return ResponseEntity.ok(list);
    }

    @GetMapping("{id_building}")
    public ResponseEntity<BuildingDetailsDTO> updateBuilding(@PathVariable Long id_building){
        var building = buildingServiceImp.getBuilding(id_building);
        return ResponseEntity.ok(building);
    }

    @PutMapping("{id_building}")
    public ResponseEntity<BuildingDetailsDTO> updateCostCenter(@PathVariable Long id_building,@RequestBody BuildingUpdateDTO dados){
        BuildingDetailsDTO updateBuilding = buildingServiceImp.updateBuilding(id_building, dados);
        return ResponseEntity.ok(updateBuilding);
    }

    @PutMapping("/inactivate/{id_building}")
    @Transactional
    public ResponseEntity<Void> inactivate(@PathVariable Long id_building){
        buildingServiceImp.inactivateBuilding(id_building);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/active/{id_building}")
    @Transactional
    public ResponseEntity<Void> active(@PathVariable Long id_building){
        buildingServiceImp.activateBuilding(id_building);
        return ResponseEntity.ok().build();
    }
}
