package com.MapView.BackEnd.dtos.User;

import com.MapView.BackEnd.dtos.Role.RoleDetailsDTO;
import com.MapView.BackEnd.entities.Role;
import com.MapView.BackEnd.entities.Users;

import java.util.List;

public record LoggedUserDetails(Long user_id, String email, String name, RoleDetailsDTO role) {
    public LoggedUserDetails(Users user){
        this(user.getId_user(), user.getEmail(), user.getName(),new RoleDetailsDTO(user.getRole()));
    }
}
