package com.MapView.BackEnd.dtos.TrackingHistory;

import com.MapView.BackEnd.entities.Environment;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Responsible;
import com.MapView.BackEnd.entities.TrackingHistory;
import com.MapView.BackEnd.enums.EnumTrackingAction;
import com.MapView.BackEnd.enums.EnumWarnings;

import java.time.LocalDateTime;
import java.util.List;

public record TrackingHistoryResponsibleDetails(Long id_tracking,
                                                LocalDateTime datetime,
                                                Equipment equipment,
                                                Environment environment,
                                                EnumTrackingAction action,
                                                EnumWarnings warning,
                                                List<Responsible> responsibles) {

    public TrackingHistoryResponsibleDetails(TrackingHistory trackingHistory, List<Responsible> responsibles) {
        this(trackingHistory.getId(), trackingHistory.getDatetime(), trackingHistory.getEquipment(),
                trackingHistory.getEnvironment(), trackingHistory.getAction(),
                trackingHistory.getWarning(),responsibles);
    }
}
