package com.MapView.BackEnd.dtos.TrackingHistory;

import com.MapView.BackEnd.enums.EnumAction;

import java.time.Instant;

public record TrackingHistoryCreateDTO(String id_equipment, Long id_enviroment, EnumAction action) {
}
