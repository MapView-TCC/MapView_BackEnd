package com.MapView.BackEnd.Controller;

import com.MapView.BackEnd.Service.AreaService;
import com.MapView.BackEnd.ServiceImp.AreaServiceImp;
import com.MapView.BackEnd.dtos.Area.AreaCreateDTO;
import com.MapView.BackEnd.dtos.Area.AreaDetailsDTO;
import com.MapView.BackEnd.dtos.Area.AreaUpdateDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterDetailsDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/area")
public class AreaController {

    @Autowired
    private AreaServiceImp areaServiceImp;

    @PostMapping
    @Transactional
    public AreaDetailsDTO createArea(@RequestBody @Valid AreaCreateDTO dados, UriComponentsBuilder uriBuilder){
        var area = areaServiceImp.createArea(dados);

        // boa pratica, para retornar o caminho
        var uri = uriBuilder.path("/api/v1/area/{id}").buildAndExpand(area.id_area()).toUri();
        return ResponseEntity.created(uri).body(new AreaDetailsDTO(area.id_area(), area.area_code(), area.area_name(), area.operative())).getBody();
    }

    @GetMapping("/{id_area}")
    public ResponseEntity<AreaDetailsDTO> getIdArea(@PathVariable Long id_area){
        var area = areaServiceImp.getArea(id_area);
        return ResponseEntity.ok(area);
    }

    @GetMapping
    public ResponseEntity<List<AreaDetailsDTO>> listArea(){
        var area = areaServiceImp.getAllArea();
        return ResponseEntity.ok(area);
    }

    @PutMapping("/{id_area}")
    @Transactional
    public ResponseEntity<AreaDetailsDTO> updateArea(@PathVariable Long id_area, @RequestBody AreaUpdateDTO dados){
        AreaDetailsDTO areaDetailsDTO = areaServiceImp.updateArea(id_area, dados);
        return ResponseEntity.ok(areaDetailsDTO);
    }

    @PutMapping("/inactivate/{id_area}")
    @Transactional
    public ResponseEntity<AreaDetailsDTO> inactivate(@PathVariable Long id_area){
        areaServiceImp.inactivateArea(id_area);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/active/{id_area}")
    @Transactional
    public ResponseEntity<AreaDetailsDTO> active(@PathVariable Long id_area){
        areaServiceImp.activateArea(id_area);
        return ResponseEntity.ok().build();
    }


}
