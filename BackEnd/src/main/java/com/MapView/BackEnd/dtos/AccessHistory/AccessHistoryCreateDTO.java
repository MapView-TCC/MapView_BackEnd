package com.MapView.BackEnd.dtos.AccessHistory;

import java.time.Instant;
import java.time.LocalDateTime;

public record AccessHistoryCreateDTO(Long id_users, LocalDateTime logout_dateTime) {
}
