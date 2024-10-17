package com.MapView.BackEnd.dtos.UserRole;

import com.MapView.BackEnd.entities.Role;
import com.MapView.BackEnd.entities.Users;

public record UserRoleCreateDTO(Users user, Role role) {
}
