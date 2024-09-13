package com.MapView.BackEnd.dtos.Area;

import jakarta.validation.constraints.NotBlank;

public record AreaUpdateDTO(
                            String area_code,

                            String area_name) {
}
