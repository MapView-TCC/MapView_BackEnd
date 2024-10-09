package com.MapView.BackEnd.dtos.FormsRegisterEnviroment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FormsRegisterEnvironmentCreateDTO (
                                                 @Min(value = 1, message = "Building Id cannot be smaller than 0.")
                                                 @Positive(message = "CostCenter Id must be Positive.")
                                                 Long id_building,
                                                 @Min(value = 1, message = "Area Id cannot be smaller than 0.")
                                                 @Positive(message = "CostCenter Id must be Positive.")
                                                 Long id_area,
                                                 String environment_name,
                                                 String raspberry_name) {
}
