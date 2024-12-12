package com.MapView.BackEnd.dtos.Location;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record LocationUpdateDTO(
                                Long post,
                                Long environment) {
}
