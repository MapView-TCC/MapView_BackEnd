package com.MapView.BackEnd.dtos.Building;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BuildingCreateDTO(@NotBlank(message = "building code cannot be blank.")
                                @NotNull(message = "building code cannot be null.")
                                String building_code) {
}
