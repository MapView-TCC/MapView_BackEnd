package com.MapView.BackEnd.dtos.TrackingHistory;

import com.MapView.BackEnd.entities.TrackingHistory;
import com.MapView.BackEnd.enums.EnumAction;

import java.time.Instant;

public record TrackingHistoryDetailsDTO(Long id_tracking, Instant dateTime, String id_equipment, Long id_enviroment, EnumAction action) {

    public TrackingHistoryDetailsDTO(TrackingHistory trackingHistory) {
        this(trackingHistory.getId_tracking(), trackingHistory.getDateTime(), trackingHistory.getId_equipment().getId_equipment(),
                trackingHistory.getId_enviroment().getId_environment(), trackingHistory.getAction());
    }
}
