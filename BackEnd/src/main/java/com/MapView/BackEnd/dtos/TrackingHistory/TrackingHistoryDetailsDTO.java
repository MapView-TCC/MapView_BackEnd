package com.MapView.BackEnd.dtos.TrackingHistory;

import com.MapView.BackEnd.entities.Enviroment;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.TrackingHistory;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.enums.EnumColors;
import com.MapView.BackEnd.enums.EnumTrackingAction;

import java.time.Instant;

public record TrackingHistoryDetailsDTO(Long id_tracking,
                                        Instant datetime,
                                        Equipment id_equipment,
                                        Enviroment id_environment,
                                        EnumTrackingAction action,
                                        EnumColors colors) {

    public TrackingHistoryDetailsDTO(TrackingHistory trackingHistory) {
        this(trackingHistory.getId_tracking(), trackingHistory.getDatetime(), trackingHistory.getId_equipment(),
                trackingHistory.getId_environment(), trackingHistory.getAction(),
                trackingHistory.getColors());
    }
}
