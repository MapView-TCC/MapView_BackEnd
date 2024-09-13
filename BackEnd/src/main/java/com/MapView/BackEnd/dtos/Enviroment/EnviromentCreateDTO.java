package com.MapView.BackEnd.dtos.Enviroment;

import com.MapView.BackEnd.entities.Raspberry;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record EnviromentCreateDTO (@NotBlank(message = "Enviroment name cannot be blank")
                                   String environment_name,
                                   @Positive
                                   @Min(1)
                                   String id_raspberry){
}
