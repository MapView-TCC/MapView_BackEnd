package com.MapView.BackEnd.dtos.Permission;

import com.MapView.BackEnd.entities.Permission;
import com.MapView.BackEnd.entities.Role;
import com.MapView.BackEnd.entities.Users;

import java.time.LocalDate;

public record PermissionDetailsDTO(Long id_permission, Users user, Role role, LocalDate date) {
    public PermissionDetailsDTO(Permission permission){
        this(permission.getId_permission(),permission.getUsers(),permission.getRole(),permission.getDate());
    }
}
