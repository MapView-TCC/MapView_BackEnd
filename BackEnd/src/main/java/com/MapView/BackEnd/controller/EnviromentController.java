package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.serviceImp.EnviromentServiceImp;
import com.MapView.BackEnd.dtos.Enviroment.EnviromentCreateDTO;
import com.MapView.BackEnd.dtos.Enviroment.EnviromentDetailsDTO;
import com.MapView.BackEnd.dtos.Enviroment.EnviromentUpdateDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enviroment")
public class EnviromentController {

    private final EnviromentServiceImp enviromentServiceImp;

    public EnviromentController(EnviromentServiceImp enviromentServiceImp) {
        this.enviromentServiceImp = enviromentServiceImp;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<EnviromentDetailsDTO> createEnviroment(EnviromentCreateDTO data, UriComponentsBuilder uriBuilder){
        var enviromnet = enviromentServiceImp.createEnviroment(data);
        var uri = uriBuilder.path("/api/v1/enviroment").buildAndExpand(enviromnet.id_enviroment()).toUri();
        return ResponseEntity.created(uri).body(new EnviromentDetailsDTO(enviromnet.id_enviroment(),enviromnet.environment_name(),enviromnet.id_raspberry()));
    }


    @PostMapping("{enviroment_id}")
    @Transactional
    public ResponseEntity<EnviromentDetailsDTO> updateEnviroment(@PathVariable("enviroment_id") Long enviroment_id, EnviromentUpdateDTO data){
        var enviroment = enviromentServiceImp.updateEnviroment(enviroment_id,data);
        return ResponseEntity.ok(enviroment);
    }

    @GetMapping("/{enviroment_id}")
    public ResponseEntity<EnviromentDetailsDTO> getEnviroment(@PathVariable("enviroment_id") Long enviroment_id){
        var enviroment = enviromentServiceImp.getEnvioment(enviroment_id);
        return ResponseEntity.ok(enviroment);

    }

    @GetMapping
    public ResponseEntity<List<EnviromentDetailsDTO>> getAllEnviroment(@RequestParam int page, @RequestParam int itens){
        var enviroment = enviromentServiceImp.getAllEnvioment(page, itens);
        return ResponseEntity.ok(enviroment);

    }

    @PutMapping("/active/{enviroment_id}")
    @Transactional
    public ResponseEntity<Void> activeEnviroment(@PathVariable("enviroment_id") Long enviroment_id){
        enviromentServiceImp.activateEnviroment(enviroment_id);
        return ResponseEntity.ok().build();

    }
    @PutMapping("/inactivate/{enviroment_id}")
    @Transactional
    public ResponseEntity<Void> inactiveEnviroment(@PathVariable("enviroment_id") Long enviroment_id){
        enviromentServiceImp.inactivateEnviroment(enviroment_id);
        return ResponseEntity.ok().build();

    }
}
