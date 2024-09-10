package com.MapView.BackEnd.dtos.User;

import com.MapView.BackEnd.enums.RoleUser;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateDTO (@NotBlank  RoleUser roleUser){
}
