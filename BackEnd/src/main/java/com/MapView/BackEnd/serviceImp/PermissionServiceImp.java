package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Permission.PermissionCreateDTO;
import com.MapView.BackEnd.entities.Permission;
import com.MapView.BackEnd.entities.Role;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import com.MapView.BackEnd.repository.PermissionRepository;
import com.MapView.BackEnd.repository.RoleRespository;
import com.MapView.BackEnd.repository.UserRepository;
import org.apache.catalina.User;
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
        Role role = this.roleRespository.findByName(permissionCreateDTO.role().toString()).orElseThrow(()-> new NotFoundException("Role not found"));

        Permission permission = new Permission(user,role);
        Permission savedPermission = permissionRepository.save(permission);

        return savedPermission;

    }

    public void acceptPermission(Long id_permission){
        Permission permission = permissionRepository.findById(id_permission).orElseThrow((()-> new NotFoundException("permission not found")));
        Users user = userRepository.findById(permission.getUsers().getId_user()).orElseThrow(()-> new NotFoundException("User not found"));
        Role role = roleRespository.findById(permission.getRole().getId()).orElseThrow(()-> new NotFoundException("User not found"));
        user.setRole(role);
    }

    public void declinedPermission(Long id_permission){
        Permission permission = permissionRepository.findById(id_permission).orElseThrow((()-> new NotFoundException("permission not found")));
        permissionRepository.delete(permission);
    }



    public List<Permission> getAll (){
        return this.permissionRepository.findAll();
    }

    public List<Permission> filterByRole(Role role){
        return this.permissionRepository.findByRole(role).orElseThrow(()-> new NotFoundException("Permissions not found"));
    }
}
