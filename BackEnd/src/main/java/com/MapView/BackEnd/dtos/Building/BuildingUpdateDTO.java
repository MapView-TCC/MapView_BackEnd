package com.MapView.BackEnd.dtos.Building;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BuildingUpdateDTO(
                                String building_code) {
}
