package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.dtos.Equipment.EquipmentCreateDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleCreateDTO;
import com.MapView.BackEnd.dtos.Location.LocationCreateDTO;
import com.MapView.BackEnd.dtos.Post.PostDetailDTO;
import com.MapView.BackEnd.dtos.RegisterDetailsDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleCrateDTO;
import com.MapView.BackEnd.serviceImp.RegisterServiceImp;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/register")

public class RegisterController {


    private final RegisterServiceImp registerServiceImp;

    public RegisterController(RegisterServiceImp registerServiceImp) {
        this.registerServiceImp = registerServiceImp;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<RegisterDetailsDTO> register(@RequestBody @Valid EquipmentCreateDTO dataEquipment,
                                                       @RequestBody @Valid LocationCreateDTO dataLocation,
                                                       @RequestBody @Valid EquipmentResponsibleCreateDTO dataResposible,
                                                       @RequestParam Long userLog_id){
        
        RegisterDetailsDTO  register = registerServiceImp.register(dataEquipment,dataLocation,dataResposible,userLog_id);
        return ResponseEntity.ok(register);

    }
}
