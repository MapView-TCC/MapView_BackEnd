package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.dtos.FormsRegisterEnviroment.FormsRegisterEnviromentDetailsDTO;
import com.MapView.BackEnd.dtos.FormsRegisterEnviroment.FormsRegisterEnvironmentCreateDTO;
import com.MapView.BackEnd.serviceImp.FormsRegisterEnvironmentImp;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/V1/registerEnvironment")

public class FormsRegisterEnvironment {

    private final FormsRegisterEnvironmentImp formsRegisterEnvironmentImp;

    public FormsRegisterEnvironment(FormsRegisterEnvironmentImp formsRegisterEnvironmentImp) {
        this.formsRegisterEnvironmentImp = formsRegisterEnvironmentImp;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<FormsRegisterEnviromentDetailsDTO> createFormsRegisterEnvironment(@RequestBody FormsRegisterEnvironmentCreateDTO data, @RequestParam Long userlog){
        FormsRegisterEnviromentDetailsDTO forms = formsRegisterEnvironmentImp.createFormsRegisterEnvironment(data,userlog);
        return  ResponseEntity.ok(forms);
    }
}