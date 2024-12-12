package com.MapView.BackEnd.dtos.TrackingHistory;

import com.MapView.BackEnd.entities.Environment;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.TrackingHistory;
import com.MapView.BackEnd.enums.EnumWarnings;
import com.MapView.BackEnd.enums.EnumTrackingAction;

import java.time.LocalDateTime;

public record TrackingHistoryDetailsDTO(Long id_tracking,
                                        LocalDateTime datetime,
                                        Equipment equipment,
                                        Environment environment,
                                        EnumTrackingAction action,
                                        EnumWarnings warning) {

    public TrackingHistoryDetailsDTO(TrackingHistory trackingHistory) {
        this(trackingHistory.getId(), trackingHistory.getDatetime(), trackingHistory.getEquipment(),
                trackingHistory.getEnvironment(), trackingHistory.getAction(),
                trackingHistory.getWarning());
    }
}