package com.MapView.BackEnd.dtos.Responsible;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ResponsibleCrateDTO(String responsible_name, String edv, Long id_classes,
                                  @Min(1)
                                  Long id_user) {
}
