package com.MapView.BackEnd.dtos.Responsible;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record ResponsibleUpdateDTO (String responsible_name,
                                    String edv,

                                    Long id_classes,
                                    Long id_user) {
}
