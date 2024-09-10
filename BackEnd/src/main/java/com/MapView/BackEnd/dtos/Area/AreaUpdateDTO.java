package com.MapView.BackEnd.dtos.Area;

import jakarta.validation.constraints.NotBlank;

public record AreaUpdateDTO(@NotBlank(message = "area code cannot be blank")
                            String area_code,
                            @NotBlank(message = "area name cannot be blank")
                            String area_name) {
}
