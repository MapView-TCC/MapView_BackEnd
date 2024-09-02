package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.dtos.MainOwner.MainOwnerCreateDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerDetailsDTO;
import com.MapView.BackEnd.serviceImp.MainOwnerServiceImp;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerUpdateDTO;
import com.MapView.BackEnd.infra.NotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mainowner")
public class MainOwnerController {


    private final MainOwnerServiceImp mainOwnerServiceImp;

    public MainOwnerController(MainOwnerServiceImp mainOwnerServiceImp) {
        this.mainOwnerServiceImp = mainOwnerServiceImp;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<MainOwnerDetailsDTO> createMainOwner(@RequestBody @Valid MainOwnerCreateDTO mainOwnerDTO,@RequestParam Long user_id, UriComponentsBuilder uriBuilder){
        var mainOwner = mainOwnerServiceImp.createMainOwner(mainOwnerDTO,user_id);

        // boa pratica, para retornar o caminho
        var uri = uriBuilder.path("/api/v1/mainowner/{id}").buildAndExpand(mainOwner.id_owner()).toUri();
        return ResponseEntity.created(uri).body(new MainOwnerDetailsDTO(mainOwner.id_owner(), mainOwner.owner_name(), mainOwner.id_cost_center(), mainOwner.operative()));
    }

    @GetMapping
    public ResponseEntity<List<MainOwnerDetailsDTO>> getAllMainOwner(@RequestParam Long user_id,@RequestParam int page, @RequestParam int itens){
        var list = mainOwnerServiceImp.getAllMainOwner(page, itens, user_id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MainOwnerDetailsDTO> getMainOwner(@RequestParam Long user_id, @PathVariable String mainowner_id){
        try {
            MainOwnerDetailsDTO mainOwner = mainOwnerServiceImp.getMainOwner(mainowner_id,user_id);
            return ResponseEntity.ok(mainOwner);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<MainOwnerDetailsDTO> updateMainOwner(@RequestParam Long user_id,@PathVariable String mainowner_id, @RequestBody MainOwnerUpdateDTO dados){
        MainOwnerDetailsDTO updateMainOwner = mainOwnerServiceImp.updateMainOwner(mainowner_id, dados,user_id);
        return ResponseEntity.ok(updateMainOwner);
    }

    @PutMapping("/inactivate/{id}")
    @Transactional
    public ResponseEntity<Void> inactivate(@RequestParam Long user_id,@PathVariable String mainowner_id){
        mainOwnerServiceImp.inactivateMainOwner(mainowner_id,user_id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/active/{id}")
    @Transactional
    public ResponseEntity<Void> active(@RequestParam Long user_id,@PathVariable String id){
        mainOwnerServiceImp.activateMainOwner(id, user_id);
        return ResponseEntity.ok().build();
    }

}
