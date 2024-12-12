package com.MapView.BackEnd.dtos.UserLog;

import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.EnumAction;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record UserLogDetailsDTO(Long id_log,
                                Users user,
                                String altered_table,
                                String id_altered,
                                String field,
                                String description,
                                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
                                LocalDateTime datetime,
                                EnumAction action) {
    public UserLogDetailsDTO(UserLog userLog){
        this(userLog.getId_log(),userLog.getUser(),userLog.getAltered_table(), userLog.getId_altered(), userLog.getField(), userLog.getDescription(), userLog.getDatetime(),userLog.getAction());
    }
}
