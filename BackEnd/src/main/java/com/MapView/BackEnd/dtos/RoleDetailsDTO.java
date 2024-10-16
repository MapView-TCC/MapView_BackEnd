package com.MapView.BackEnd.dtos;

import com.MapView.BackEnd.entities.Role;

public record RoleDetailsDTO(Long id_role, String name_role) {

    public RoleDetailsDTO(Role role){
        this(role.getId(), role.getName());
    }
}
