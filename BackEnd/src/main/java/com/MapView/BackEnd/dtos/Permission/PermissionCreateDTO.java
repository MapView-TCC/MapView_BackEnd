package com.MapView.BackEnd.dtos.Permission;

import com.MapView.BackEnd.enums.EnumRole;

public record PermissionCreateDTO(Long id_user, EnumRole role) {
}
