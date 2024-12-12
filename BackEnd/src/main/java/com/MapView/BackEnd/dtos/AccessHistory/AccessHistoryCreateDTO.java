package com.MapView.BackEnd.dtos.AccessHistory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.time.LocalDateTime;

public record AccessHistoryCreateDTO(@Positive(message = "user id cannot be negative.")
                                     @Min(value = 1 )
                                     Long user,
                                     LocalDateTime logout_dateTime
) {
}
