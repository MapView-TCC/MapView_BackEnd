package com.MapView.BackEnd.dtos;

import com.MapView.BackEnd.entities.Permission;

import java.time.LocalDate;

public record PermissionDetailsDTO(Long id_permission, String email,String name, String role, LocalDate date) {
    public PermissionDetailsDTO(Permission permission){
        this(permission.getId_permission(),permission.getUsers().getEmail(),permission.getUsers().getName(),permission.getRole().getName(),permission.getDate());
    }
}
