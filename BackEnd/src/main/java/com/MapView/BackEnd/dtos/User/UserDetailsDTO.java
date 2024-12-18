package com.MapView.BackEnd.dtos.User;

import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.RoleUser;

public record UserDetailsDTO(Long id,String email, RoleUser roleUser) {
    public UserDetailsDTO(Users user){
        this(user.getId_user(),user.getEmail(),user.getRole());
    }
}
