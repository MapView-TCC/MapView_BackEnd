package com.MapView.BackEnd.dtos.Enviroment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record EnviromentUpdateDTO(
                                  String environment_name,
                                  String id_raspberry) {
}
