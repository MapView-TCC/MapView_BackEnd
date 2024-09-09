package com.MapView.BackEnd.dtos.Area;

import jakarta.validation.constraints.NotBlank;

public record AreaCreateDTO(@NotBlank (message = "area code cannot be null")
                            String area_code,
                            @NotBlank(message = "area name cannot be null")
                            String area_name) {
}
