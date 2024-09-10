package com.MapView.BackEnd.dtos.Location;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record LocationUpdateDTO(@Min(value = 1, message = "Post Id cannot be smaller than 0")
                                @Positive(message = "Post Id must be Positive")
                                Long id_post,
                                @Min(value = 1, message = "Post Id cannot be smaller than 0")
                                @Positive(message = "Post Id must be Positive")
                                Long id_enviromnet) {
}
