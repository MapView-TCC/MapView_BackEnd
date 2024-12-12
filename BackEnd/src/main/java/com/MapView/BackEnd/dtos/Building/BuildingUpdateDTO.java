package com.MapView.BackEnd.dtos.Building;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BuildingUpdateDTO(@NotBlank(message = "Building code cannot be null")
                                @NotBlank(message = "Building code cannot be blank")
                                String building_code) {
}
