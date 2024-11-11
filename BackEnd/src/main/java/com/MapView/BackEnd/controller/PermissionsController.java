package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.dtos.Notification.NotificationDetailsDTO;
import com.MapView.BackEnd.dtos.PermissionCreateDTO;
import com.MapView.BackEnd.dtos.PermissionDetailsDTO;
import com.MapView.BackEnd.entities.Role;
import com.MapView.BackEnd.serviceImp.PermissionServiceImp;
import jakarta.transaction.Transactional;
import org.apache.commons.math3.optim.nonlinear.scalar.LineSearch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("ap1/v1/permissions")
public class PermissionsController {

    private final PermissionServiceImp permissionServiceImp;

    public PermissionsController(PermissionServiceImp permissionServiceImp) {
        this.permissionServiceImp = permissionServiceImp;
    }


    @GetMapping
    public List<PermissionDetailsDTO> getAll (){
        return this.permissionServiceImp.getAll().stream().map(PermissionDetailsDTO::new).toList();
    }
    @GetMapping("/filterRole")
    public List<PermissionDetailsDTO> getByRole(Role role){
        return this.permissionServiceImp.filterByRole(role).stream().map(PermissionDetailsDTO::new).toList();
    }
    @PostMapping
    @Transactional
    public PermissionDetailsDTO createPermission(PermissionCreateDTO permissionCreateDTO){
        return new PermissionDetailsDTO(this.permissionServiceImp.createPermission(permissionCreateDTO));
    }
}
