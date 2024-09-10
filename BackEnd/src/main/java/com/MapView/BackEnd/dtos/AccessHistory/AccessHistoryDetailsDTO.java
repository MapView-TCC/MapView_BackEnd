package com.MapView.BackEnd.dtos.AccessHistory;

import com.MapView.BackEnd.entities.AccessHistory;
import com.MapView.BackEnd.entities.Users;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.time.LocalDateTime;

public record AccessHistoryDetailsDTO(Long id_history,
                                      Users users,
                                      Instant login_dateTime,
                                      @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm ")
                                      LocalDateTime logout_dateTime) {
    public AccessHistoryDetailsDTO(AccessHistory history) {
        this(history.getId_history(), history.getUser(), history.getLogin_datetime(), history.getLogout_datetime());
    }


}
