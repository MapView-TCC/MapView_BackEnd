package com.MapView.BackEnd.dtos.Responsible;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ResponsibleCrateDTO(@NotBlank(message = "Responsible name name id cannot be blank.")
                                  String responsible,
                                  @NotBlank(message = "Edv id cannot be blank.")
                                  String edv,
                                  @Min(value = 1, message = "User Id cannot be smaller than 0.")
                                  @Positive(message = "Classes Id must be Positive.")
                                  Long classes,
                                  @Min(value = 1, message = "User Id cannot be smaller than 0.")
                                  @Positive(message = "User Id must be Positive.")
                                  Long user) {

}
