package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.dtos.FormsRegisterEnviroment.FormsRegisterEnviromentDetailsDTO;
import com.MapView.BackEnd.dtos.FormsRegisterEnviroment.FormsRegisterEnvironmentCreateDTO;
import com.MapView.BackEnd.serviceImp.FormsRegisterEnviromentImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/V1/registerEnvironment")

public class FormsRegisterEnviroment {

    private final FormsRegisterEnviromentImp formsRegisterEnviromentImp;

    public FormsRegisterEnviroment(FormsRegisterEnviromentImp formsRegisterEnviromentImp) {
        this.formsRegisterEnviromentImp = formsRegisterEnviromentImp;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<FormsRegisterEnviromentDetailsDTO> createFormsRegisterEnvironment(FormsRegisterEnvironmentCreateDTO data, @RequestParam Long userlog){
        FormsRegisterEnviromentDetailsDTO forms = formsRegisterEnviromentImp.createFormsRegisterEnvironment(data,userlog);
        return  ResponseEntity.ok(forms);
    }
}
