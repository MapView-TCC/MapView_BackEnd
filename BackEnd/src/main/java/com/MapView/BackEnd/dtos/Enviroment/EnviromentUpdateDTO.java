package com.MapView.BackEnd.dtos.Enviroment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record EnviromentUpdateDTO(@NotBlank(message = "Enviroment name cannot be blank")
                                  String environment_name,
                                  @Min(value = 1,message = "Id cannot be smaller than 0")
                                  @Positive
                                  String id_raspberry) {
}
