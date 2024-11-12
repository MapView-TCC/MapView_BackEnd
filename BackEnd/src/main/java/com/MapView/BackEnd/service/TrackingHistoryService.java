package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryCreateDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryDetailsDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryWrongLocationDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrakingHistoryResponsibleDetails;
import com.MapView.BackEnd.enums.EnumWarnings;
import com.MapView.BackEnd.enums.EnumTrackingAction;

import java.util.List;

public interface TrackingHistoryService {

    TrackingHistoryDetailsDTO getTrackingHistory(Long id_tracking);
    List<TrackingHistoryDetailsDTO> getAllTrackingHistory();
    TrakingHistoryResponsibleDetails createTrackingHistory(TrackingHistoryCreateDTO dados);
    List<TrackingHistoryDetailsDTO> FilterTracking(int page, int itens, EnumTrackingAction action,
                                                   Integer day, Integer month, Integer year, EnumWarnings colors,
                                                   String id_equipment);
    List<TrackingHistoryWrongLocationDTO> findWrongLocationEquipments (Long id_environment);
    void deleteTrackingById(Long id_tracking);
}
