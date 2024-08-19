package com.MapView.BackEnd.Dtos.User;

import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.RoleUser;

public record UserDetailsDto(String email, RoleUser roleUser) {
    public UserDetailsDto(Users user){
        this(user.getEmail(),user.getRole());
    }
}
