package com.MapView.BackEnd.dtos.Responsible;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record ResponsibleUpdateDTO (String responsible,
                                    String edv,
                                    Long classes,
                                    Long user) {
}
