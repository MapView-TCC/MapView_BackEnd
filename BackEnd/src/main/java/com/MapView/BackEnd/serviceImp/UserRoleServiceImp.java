package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.UserRole.UserRoleDetailsDTO;
import com.MapView.BackEnd.entities.Role;
import com.MapView.BackEnd.entities.UserRole;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.repository.UserRoleRepository;
import com.MapView.BackEnd.service.UserRoleService;

public class UserRoleServiceImp implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final RoleServiceImp roleServiceImp;


    public UserRoleServiceImp(UserRoleRepository userRoleRepository, RoleServiceImp roleServiceImp) {
        this.userRoleRepository = userRoleRepository;
        this.roleServiceImp = roleServiceImp;
    }

    public UserRole getUserRoleByUser(Users users){
        UserRole userRole = userRoleRepository.findByUser(users).orElse(roleUserNotFound(users));
        return userRole;

    }


    public UserRoleDetailsDTO createUserRole(Users user,Role role){
        UserRole userRole = new UserRole(user,role);
        return new UserRoleDetailsDTO(userRole);

    }

    public UserRole roleUserNotFound(Users users){
        Role role = roleServiceImp.getRolebyName("ROLE_APRENDIZ");
        UserRole userRole = new UserRole(users,role);
        return userRole;

    }
}
