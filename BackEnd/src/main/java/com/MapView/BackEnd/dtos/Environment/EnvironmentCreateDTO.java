package com.MapView.BackEnd.dtos.Environment;

import jakarta.validation.constraints.NotBlank;

public record EnvironmentCreateDTO(@NotBlank(message = "Environment name cannot be blank")
                                   String environment_name,
                                   String raspberry){
}
