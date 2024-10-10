package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.dtos.FormsRegisterEnviroment.FormsRegisterEnviromentDetailsDTO;
import com.MapView.BackEnd.dtos.FormsRegisterEnviroment.FormsRegisterEnvironmentCreateDTO;
import com.MapView.BackEnd.serviceImp.FormsRegisterEnviromentImp;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/V1/registerEnvironment")

public class FormsRegisterEnviroment {

    private final FormsRegisterEnviromentImp formsRegisterEnviromentImp;

    public FormsRegisterEnviroment(FormsRegisterEnviromentImp formsRegisterEnviromentImp) {
        this.formsRegisterEnviromentImp = formsRegisterEnviromentImp;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<FormsRegisterEnviromentDetailsDTO> createFormsRegisterEnvironment(@RequestBody FormsRegisterEnvironmentCreateDTO data, @RequestParam Long userlog){
        FormsRegisterEnviromentDetailsDTO forms = formsRegisterEnviromentImp.createFormsRegisterEnvironment(data,userlog);
        return  ResponseEntity.ok(forms);
    }
}
