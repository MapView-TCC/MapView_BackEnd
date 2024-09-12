package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.serviceImp.AreaServiceImp;
import com.MapView.BackEnd.dtos.Area.AreaCreateDTO;
import com.MapView.BackEnd.dtos.Area.AreaDetailsDTO;
import com.MapView.BackEnd.dtos.Area.AreaUpdateDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/area")
public class AreaController {


    private final AreaServiceImp areaServiceImp;

    public AreaController(AreaServiceImp areaServiceImp) {
        this.areaServiceImp = areaServiceImp;
    }

    @PostMapping
    @Transactional
    public AreaDetailsDTO createArea(@RequestBody @Valid AreaCreateDTO dados,@RequestParam Long userLog_id, UriComponentsBuilder uriBuilder){
        var area = areaServiceImp.createArea(dados,userLog_id);

        // boa pratica, para retornar o caminho
        var uri = uriBuilder.path("/api/v1/area/{id}").buildAndExpand(area.id_area()).toUri();
        return ResponseEntity.created(uri).body(new AreaDetailsDTO(area.id_area(), area.area_code(), area.area_name())).getBody();
    }

    @GetMapping("/{id_area}")
    public ResponseEntity<AreaDetailsDTO> getArea(@RequestParam Long userLog_id, @PathVariable Long id_area){
        var area = areaServiceImp.getArea(id_area,userLog_id);
        return ResponseEntity.ok(area);
    }

    @GetMapping
    public ResponseEntity<List<AreaDetailsDTO>>  getAllArea(@RequestParam int page, @RequestParam int itens,@RequestParam Long userLog_id){
        var area = areaServiceImp.getAllArea(page, itens,userLog_id);
        return ResponseEntity.ok(area);
    }

    @PutMapping("/{id_area}")
    @Transactional
    public ResponseEntity<AreaDetailsDTO> updateArea(@PathVariable Long id_area, @RequestBody AreaUpdateDTO dados,@RequestParam Long userLog_id){
        AreaDetailsDTO areaDetailsDTO = areaServiceImp.updateArea(id_area, dados,userLog_id);
        return ResponseEntity.ok(areaDetailsDTO);
    }

    @PutMapping("/inactivate/{id_area}")
    @Transactional
    public ResponseEntity<AreaDetailsDTO> inactivate(@PathVariable Long id_area,@RequestParam Long userLog_id){
        areaServiceImp.inactivateArea(id_area,userLog_id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/active/{id_area}")
    @Transactional
    public ResponseEntity<AreaDetailsDTO> active( @PathVariable Long id_area,@RequestParam Long userLog_id){
        areaServiceImp.activateArea(id_area,userLog_id);
        return ResponseEntity.ok().build();
    }


}
