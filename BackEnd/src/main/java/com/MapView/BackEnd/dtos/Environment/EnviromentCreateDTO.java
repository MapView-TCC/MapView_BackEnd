package com.MapView.BackEnd.dtos.Environment;

import jakarta.validation.constraints.NotBlank;

public record EnviromentCreateDTO (@NotBlank(message = "Enviroment name cannot be blank")
                                   String environment_name,
                                   String id_raspberry){
}
