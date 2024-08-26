package com.MapView.BackEnd.Controller;

import com.MapView.BackEnd.ServiceImp.RaspberryServiceImp;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryCreateDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryDetailsDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryUpdateDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/raspberry")
public class RaspberryController {


    private final RaspberryServiceImp raspberryServiceImp;

    public RaspberryController(RaspberryServiceImp raspberryServiceImp) {
        this.raspberryServiceImp = raspberryServiceImp;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<RaspberryDetailsDTO> cadastroRaspberry(@RequestBody @Valid RaspberryCreateDTO raspberryCreateDTO, UriComponentsBuilder uriBuilder){
        var raspberry = raspberryServiceImp.createRaspberry(raspberryCreateDTO);

        // boa pratica, para retornar o caminho
        var uri = uriBuilder.path("/api/v1/raspberry/{id}").buildAndExpand(raspberry.id_raspberry()).toUri();
        return ResponseEntity.created(uri).body(new RaspberryDetailsDTO(raspberry.id_raspberry(),raspberry.raspberry_name(), raspberry.id_building(), raspberry.id_area(), raspberry.operative()));
    }

    @GetMapping
    public ResponseEntity<List<RaspberryDetailsDTO>> getAllRaspberry(){
        var list = raspberryServiceImp.getAllRaspberry();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RaspberryDetailsDTO> getRaspberry(@PathVariable Long id){
        RaspberryDetailsDTO raspberry = raspberryServiceImp.getRaspberry(id);
        return ResponseEntity.ok(raspberry);
    }

    @PutMapping("/{id_raspberry}")
    @Transactional
    public ResponseEntity<RaspberryDetailsDTO> updateRaspberry(@PathVariable Long id_raspberry, @RequestBody RaspberryUpdateDTO dados){
        RaspberryDetailsDTO updateRaspberry = raspberryServiceImp.updateRaspberry(id_raspberry, dados);
        return ResponseEntity.ok(updateRaspberry);
    }

    @PutMapping("/inactivate/{id}")
    @Transactional
    public ResponseEntity<Void> inactivate(@PathVariable Long id){
        raspberryServiceImp.inactivateRaspberry(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/active/{id}")
    @Transactional
    public ResponseEntity<Void> active(@PathVariable Long id){
        raspberryServiceImp.activeRaspberry(id);
        return ResponseEntity.ok().build();
    }




}
