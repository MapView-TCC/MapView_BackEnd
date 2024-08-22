package com.MapView.BackEnd.Controller;

import com.MapView.BackEnd.ServiceImp.EnviromentServiceImp;
import com.MapView.BackEnd.dtos.Enviroment.EnviromentCreateDTO;
import com.MapView.BackEnd.dtos.Enviroment.EnviromentDetailsDTO;
import com.MapView.BackEnd.dtos.Enviroment.EnviromentUpdateDTO;
import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
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
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{enviroment_id}")
    public ResponseEntity<EnviromentDetailsDTO> getUser(@PathVariable("enviroment_id") Long enviroment_id){
        var enviroment = enviromentServiceImp.getEnvioment(enviroment_id);
        return ResponseEntity.ok(enviroment);

    }

    @GetMapping
    public ResponseEntity<List<EnviromentDetailsDTO>> getAllUser(){
        var enviroment = enviromentServiceImp.getAllEnvioment();
        return ResponseEntity.ok(enviroment);

    }

    @PutMapping("/active/{enviroment_id}")
    @Transactional
    public ResponseEntity<Void> activeUser(@PathVariable("enviroment_id") Long enviroment_id){
        enviromentServiceImp.activateEnviroment(enviroment_id);
        return ResponseEntity.ok().build();

    }
    @PutMapping("/deactive/{enviroment_id}")
    @Transactional
    public ResponseEntity<Void> inactiveUser(@PathVariable("enviroment_id") Long enviroment_id){
        enviromentServiceImp.inactivateEnviroment(enviroment_id);
        return ResponseEntity.ok().build();

    }
}
