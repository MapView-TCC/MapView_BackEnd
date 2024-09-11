package com.MapView.BackEnd.dtos.TrackingHistory;

import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.enums.EnumColors;
import com.MapView.BackEnd.enums.EnumTrackingAction;

import java.time.Instant;

public record TrackingHistoryCreateDTO(Long rfid,
                                       Long id_environment
                                       ) {
}
