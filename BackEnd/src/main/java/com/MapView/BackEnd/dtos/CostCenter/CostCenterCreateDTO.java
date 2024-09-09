package com.MapView.BackEnd.dtos.CostCenter;

import jakarta.validation.constraints.NotBlank;

public record CostCenterCreateDTO(@NotBlank
                                  String cost_center_name) {
}
