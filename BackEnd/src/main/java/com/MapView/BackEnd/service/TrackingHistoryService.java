package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryCreateDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryDetailsDTO;

import java.util.List;

public interface TrackingHistoryService {

    TrackingHistoryDetailsDTO getTrackingHistory(Long id_tracking);
    List<TrackingHistoryDetailsDTO> getAllTrackingHistory();
    TrackingHistoryDetailsDTO createTrackingHistory(TrackingHistoryCreateDTO dados);

}
