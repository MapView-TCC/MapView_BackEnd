package com.MapView.BackEnd.dtos.TrackingHistory;

import com.MapView.BackEnd.entities.TrackingHistory;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.enums.EnumTrackingAction;

import java.time.Instant;

public record TrackingHistoryDetailsDTO(Long id_tracking, Instant datetime, String id_equipment, Long id_enviroment, EnumTrackingAction action) {

    public TrackingHistoryDetailsDTO(TrackingHistory trackingHistory) {
        this(trackingHistory.getId_tracking(), trackingHistory.getDatetime(), trackingHistory.getId_equipment().getId_equipment(),
                trackingHistory.getId_enviroment().getId_environment(), trackingHistory.getAction());
    }
}
