package com.MapView.BackEnd.Controller;

import com.MapView.BackEnd.dtos.CostCenter.CostCenterDetailsDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerCreateDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerDetailsDTO;
import com.MapView.BackEnd.ServiceImp.MainOwnerServiceImp;
import com.MapView.BackEnd.entities.MainOwner;
import com.MapView.BackEnd.infra.NotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mainowner")
public class MainOwnerController {

    @Autowired
    private MainOwnerServiceImp mainOwnerServiceImp;

    @PostMapping
    @Transactional
    public ResponseEntity<MainOwnerDetailsDTO> cadastro(@RequestBody @Valid MainOwnerCreateDTO mainOwnerDTO, UriComponentsBuilder uriBuilder){
        var mainOwner = mainOwnerServiceImp.createMainOwner(mainOwnerDTO);

        // boa pratica, para retornar o caminho
        var uri = uriBuilder.path("/api/v1/mainowner/{id}").buildAndExpand(mainOwner.id_owner()).toUri();
        return ResponseEntity.created(uri).body(new MainOwnerDetailsDTO(mainOwner.id_owner(), mainOwner.owner_name(), mainOwner.id_cost_center(), mainOwner.operative()));
    }

    @GetMapping
    public ResponseEntity<List<MainOwnerDetailsDTO>> getAllMainOwner(){
        var list = mainOwnerServiceImp.getAllMainOwner();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MainOwnerDetailsDTO> getMainOwner(@PathVariable String id){
        try {
            MainOwnerDetailsDTO mainOwner = mainOwnerServiceImp.getMainOwner(id);
            return ResponseEntity.ok(mainOwner);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/inactivate/{id}")
    @Transactional
    public ResponseEntity<Void> inactivate(@PathVariable String id){
        mainOwnerServiceImp.inactivateMainOwner(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/active/{id}")
    @Transactional
    public ResponseEntity<Void> active(@PathVariable String id){
        mainOwnerServiceImp.activateMainOwner(id);
        return ResponseEntity.ok().build();
    }

}
