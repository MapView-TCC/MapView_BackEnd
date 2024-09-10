package com.MapView.BackEnd.dtos.MainOwner;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record MainOwnerCreateDTO(@NotBlank(message = "Owner id cannot be blank.")
                                 String id_owner,
                                 @NotBlank(message = "Owner name cannot be blank.")
                                 String owner_name,
                                 @Min(value = 1, message = "CostCenter Id cannot be smaller than 0.")
                                 @Positive(message = "CostCenter Id must be Positive.")
                                 Long id_cost_center) {
}
