package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.dtos.Role.RoleCreateDTO;
import com.MapView.BackEnd.dtos.Role.RoleDetailsDTO;
import com.MapView.BackEnd.serviceImp.RoleServiceImp;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("ap1/v1/role")
public class RoleController {
    private final RoleServiceImp roleServiceImp;

    public RoleController(RoleServiceImp roleServiceImp) {
        this.roleServiceImp = roleServiceImp;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<RoleDetailsDTO> createRole(@RequestBody RoleCreateDTO data, UriComponentsBuilder uriBuilder){
        var roleDetailsDTO = roleServiceImp.createRole(data);
        var uri = uriBuilder.path("/api/v1/role/{id}").buildAndExpand(roleDetailsDTO.id_role()).toUri();
        return ResponseEntity.created(uri).body(new RoleDetailsDTO(roleDetailsDTO.id_role(),roleDetailsDTO.name_role()));

    }
}
