package com.MapView.BackEnd.dtos.CostCenter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CostCenterUpdateDTO(@NotBlank(message = "costCenter name cannot be blank")
                                  @NotNull(message = "costCenter name cannot be null")
                                  String costCenter_name) {
}
