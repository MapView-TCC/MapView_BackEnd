package com.MapView.BackEnd.Controller;

import com.MapView.BackEnd.dtos.CostCenter.CostCenterCreateDTO;
import com.MapView.BackEnd.ServiceImp.CostCenterServiceImp;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterDetailsDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterUpdateDTO;
import com.MapView.BackEnd.entities.CostCenter;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/costcenter")
public class CostCenterController {

    @Autowired
    private CostCenterServiceImp costCenterServiceImp;

    @PostMapping
    @Transactional
    public ResponseEntity<CostCenterDetailsDTO> cadastro(@RequestBody @Valid CostCenterCreateDTO dados, UriComponentsBuilder uriBuilder){
        var costcenter = costCenterServiceImp.createCostCenter(dados);

        // boa pratica, para retornar o caminho
        var uri = uriBuilder.path("/api/v1/costcenter/{id}").buildAndExpand(costcenter.id_cost_center()).toUri();
        return ResponseEntity.created(uri).body(new CostCenterDetailsDTO(costcenter.id_cost_center(), costcenter.cost_center_name(), costcenter.operative()));
    }

    @GetMapping
    public ResponseEntity<List<CostCenterDetailsDTO>> listResponseEntity(){
        var list = costCenterServiceImp.getAllCostCenter();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CostCenterDetailsDTO> detailCostCenter(@PathVariable Long id){
        var costCenter = costCenterServiceImp.getCostCenter(id);
        return ResponseEntity.ok(costCenter);
    }

    @PutMapping("{id}")
    public ResponseEntity<CostCenterDetailsDTO> updateCostCenter(@PathVariable Long id,@RequestBody CostCenterUpdateDTO dados){
        CostCenterDetailsDTO updateCost = costCenterServiceImp.updateCostCenter(id, dados);
        return ResponseEntity.ok(updateCost);
    }

    @PutMapping("/inactivate/{id}")
    @Transactional
    public ResponseEntity<Void> inactivate(@PathVariable Long id){
        costCenterServiceImp.inactivateCostCenter(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/active/{id}")
    @Transactional
    public ResponseEntity<Void> active(@PathVariable Long id){
        costCenterServiceImp.activateCostCenter(id);
        return ResponseEntity.ok().build();
    }
}
