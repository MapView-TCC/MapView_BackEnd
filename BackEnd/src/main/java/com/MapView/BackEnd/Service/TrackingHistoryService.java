package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.dtos.AccessHistory.AccessHistoryCreateDTO;
import com.MapView.BackEnd.dtos.AccessHistory.AccessHistoryDetailsDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryCreateDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryDetailsDTO;

import java.util.List;

public interface TrackingHistoryService {

    TrackingHistoryDetailsDTO getTrackingHistory(Long id_tracking);
    List<TrackingHistoryDetailsDTO> getAllTrackingHistory();
    TrackingHistoryDetailsDTO createTrackingHistory(TrackingHistoryCreateDTO dados);

}
