package com.MapView.BackEnd.dtos.Area;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AreaCreateDTO(@NotBlank (message = "area code cannot be blank.")
                            @NotNull(message = "area code cannot be null.")
                            String area_code,
                            @NotBlank(message = "area name cannot be blank.")
                            @NotNull(message = "area name cannot be null.")
                            String area_name) {
}
