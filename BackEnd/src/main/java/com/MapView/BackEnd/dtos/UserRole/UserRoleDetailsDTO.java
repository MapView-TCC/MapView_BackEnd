package com.MapView.BackEnd.dtos.UserRole;

import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import com.MapView.BackEnd.entities.Role;
import com.MapView.BackEnd.entities.UserRole;
import com.MapView.BackEnd.entities.Users;

public record UserRoleDetailsDTO (Long userRole_id,Long user_id, String email, Long role_id, String role_name) {
    public UserRoleDetailsDTO(UserRole userRole){
        this(userRole.getId(),
                userRole.getUser().getId_user(),
                userRole.getUser().getEmail(),
                userRole.getRole().getId(),
                userRole.getRole().getName());
    }


}
