package com.MapView.BackEnd.dtos.UserLog;

import com.MapView.BackEnd.enums.EnumAction;

public record UserLogCreateDTO(Long user_id,
                               String altered_table,
                               String id_altered,
                               String field,
                               String description,
                               EnumAction action) {
}
