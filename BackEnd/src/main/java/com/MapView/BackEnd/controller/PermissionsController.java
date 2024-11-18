package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.dtos.Permission.PermissionCreateDTO;
import com.MapView.BackEnd.dtos.Permission.PermissionDetailsDTO;
import com.MapView.BackEnd.entities.Role;
import com.MapView.BackEnd.enums.EnumRole;
import com.MapView.BackEnd.serviceImp.PermissionServiceImp;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/permissions")
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

    @PostMapping("/decline")
    @Transactional
    public ResponseEntity<Void> declinePermission(@RequestParam Long id_permission){
        this.permissionServiceImp.declinedPermission(id_permission);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept")
    @Transactional
    public ResponseEntity<Void> acceptPermission(@RequestParam Long id_permission){
        this.permissionServiceImp.acceptPermission(id_permission);
        return ResponseEntity.ok().build();
    }
    @PostMapping
    @Transactional
    public PermissionDetailsDTO createPermission(@RequestParam Long user_id, @RequestParam EnumRole role){
        return new PermissionDetailsDTO(this.permissionServiceImp.createPermission(user_id,role));
    }


}