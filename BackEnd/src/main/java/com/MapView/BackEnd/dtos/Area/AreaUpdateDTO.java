package com.MapView.BackEnd.dtos.Area;

import jakarta.validation.constraints.NotBlank;

public record AreaUpdateDTO(@NotBlank String area_code, @NotBlank String area_name) {
}
