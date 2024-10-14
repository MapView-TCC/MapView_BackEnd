package com.MapView.BackEnd.dtos.User;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public record UserCreateDTO(@Email
                            @NotBlank
                            String email,
                            String role) {


}
