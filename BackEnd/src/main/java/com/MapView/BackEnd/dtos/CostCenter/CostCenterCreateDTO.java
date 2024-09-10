package com.MapView.BackEnd.dtos.CostCenter;

import jakarta.validation.constraints.NotBlank;

public record CostCenterCreateDTO(@NotBlank(message = "CostCenter name cannot be blank")
                                  String costCenter_name) {
}
