package com.MapView.BackEnd.dtos.UserLog;

import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.EnumAction;
import org.apache.catalina.User;

import java.time.Instant;
import java.time.LocalDateTime;

public record UserLogDetailDTO (Long id_log, Users user, String altered_table, Long id_altered, String field, String description, Instant datetime, EnumAction action) {
    public UserLogDetailDTO (UserLog userLog){
        this(userLog.getId_log(),userLog.getUser(),userLog.getAltered_table(), userLog.getId_altered(), userLog.getField(), userLog.getDescription(), userLog.getDatetime(),userLog.getAction());
    }
}
