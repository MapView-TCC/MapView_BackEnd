package com.MapView.BackEnd.dtos.Location;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record LocationCreateDTO(@Positive
                                @Min(1)
                                Long post,
                                @Positive
                                @Min(1)
                                Long environment) {
}
