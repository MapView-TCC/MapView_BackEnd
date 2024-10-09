package com.MapView.BackEnd.dtos.Register;

import com.MapView.BackEnd.enums.EnumCourse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ResponsibleResgisterDTO(
                                      String responsible_name,
                                      String edv,
                                      EnumCourse enumCourse,
                                      String name_classes)  {
}
