package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.PermissionCreateDTO;
import com.MapView.BackEnd.entities.Permission;
import com.MapView.BackEnd.entities.Role;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import com.MapView.BackEnd.repository.PermissionRepository;
import com.MapView.BackEnd.repository.RoleRespository;
import com.MapView.BackEnd.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermissionServiceImp {

    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final RoleRespository roleRespository;

    public PermissionServiceImp(PermissionRepository permissionRepository, UserRepository userRepository, RoleRespository roleRespository) {
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
        this.roleRespository = roleRespository;
    }

    public Permission createPermission(PermissionCreateDTO permissionCreateDTO){
        Users user = this.userRepository.findById(permissionCreateDTO.id_user()).orElseThrow(()-> new NotFoundException("User not found"));
        Role role = this.roleRespository.findById(permissionCreateDTO.id_role()).orElseThrow(()-> new NotFoundException("Role not found"));

        Permission permission = new Permission(user,role);
        Permission savedPermission = permissionRepository.save(permission);

        return savedPermission;

    }


    public List<Permission> getAll (){
        return this.permissionRepository.findAll();
    }

    public List<Permission> filterByRole(Role role){
        return this.permissionRepository.findByRole(role).orElseThrow(()-> new NotFoundException("Permissions not found"));
    }
}
