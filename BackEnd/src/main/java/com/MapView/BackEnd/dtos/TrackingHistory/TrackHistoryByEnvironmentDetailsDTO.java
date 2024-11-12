package com.MapView.BackEnd.dtos.TrackingHistory;

import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.enums.EnumTrackingAction;
import com.MapView.BackEnd.enums.EnumWarnings;

import java.time.LocalDateTime;
import java.util.List;

public record TrackHistoryByEnvironmentDetailsDTO(Long id_tracking,
                                                  LocalDateTime datetime,
                                                  Equipment equipment,
                                                  Environment environment,
                                                  EnumTrackingAction action,
                                                  EnumWarnings warning,
                                                  List<Responsible> responsibles) {


}
