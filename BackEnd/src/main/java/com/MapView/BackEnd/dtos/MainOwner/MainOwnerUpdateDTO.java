package com.MapView.BackEnd.dtos.MainOwner;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record MainOwnerUpdateDTO(
                                 String cod_owner,
                                 Long costCenter) {
}