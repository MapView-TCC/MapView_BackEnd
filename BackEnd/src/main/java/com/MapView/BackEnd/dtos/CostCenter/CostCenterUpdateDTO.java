package com.MapView.BackEnd.dtos.CostCenter;

import jakarta.validation.constraints.NotBlank;

public record CostCenterUpdateDTO(@NotBlank(message = "costCenter name cannot be blank")
                                  String costCenter_name) {
}
