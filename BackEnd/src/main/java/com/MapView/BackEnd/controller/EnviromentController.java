package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.serviceImp.EnviromentServiceImp;
import com.MapView.BackEnd.dtos.Enviroment.EnviromentCreateDTO;
import com.MapView.BackEnd.dtos.Enviroment.EnviromentDetailsDTO;
import com.MapView.BackEnd.dtos.Enviroment.EnviromentUpdateDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
    public ResponseEntity<EnviromentDetailsDTO> createEnviroment(@RequestBody @Valid EnviromentCreateDTO data, @RequestParam Long userLog_id, UriComponentsBuilder uriBuilder){
        var enviroment = enviromentServiceImp.createEnviroment(data, userLog_id);
        var uri = uriBuilder.path("/api/v1/enviroment/{id}").buildAndExpand(enviroment.id_enviroment()).toUri();
        return ResponseEntity.created(uri).body(new EnviromentDetailsDTO(enviroment.id_enviroment(),enviroment.environment_name(),enviroment.raspberry()));
    }


    @PostMapping("{enviroment_id}")
    @Transactional
    public ResponseEntity<EnviromentDetailsDTO> updateEnviroment(@PathVariable("enviroment_id") Long enviroment_id, @RequestBody @Valid EnviromentUpdateDTO data, @RequestParam Long userLog_id){
        var enviroment = enviromentServiceImp.updateEnviroment(enviroment_id, data, userLog_id);
        return ResponseEntity.ok(enviroment);
    }

    @GetMapping("/{enviroment_id}")
    public ResponseEntity<EnviromentDetailsDTO> getEnviroment(@PathVariable("enviroment_id") Long enviroment_id, @RequestParam Long userLog_id){
        var enviroment = enviromentServiceImp.getEnviroment(enviroment_id, userLog_id);
        return ResponseEntity.ok(enviroment);

    }

    @GetMapping
    public ResponseEntity<List<EnviromentDetailsDTO>> getAllEnviroment(@RequestParam int page, @RequestParam int itens, @RequestParam Long userLog_id){
        var enviroment = enviromentServiceImp.getAllEnviroment(page, itens, userLog_id);
        return ResponseEntity.ok(enviroment);

    }

    @PutMapping("/active/{enviroment_id}")
    @Transactional
    public ResponseEntity<Void> activeEnviroment(@PathVariable("enviroment_id") Long enviroment_id, @RequestParam Long userLog_id){
        enviromentServiceImp.activateEnviroment(enviroment_id, userLog_id);
        return ResponseEntity.ok().build();

    }
    @PutMapping("/inactivate/{enviroment_id}")
    @Transactional
    public ResponseEntity<Void> inactiveEnviroment(@PathVariable("enviroment_id") Long enviroment_id, @RequestParam Long userLog_id){
        enviromentServiceImp.inactivateEnviroment(enviroment_id, userLog_id);
        return ResponseEntity.ok().build();

    }
}
