package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryCreateDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryDetailsDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface TrackingHistoryService {

    TrackingHistoryDetailsDTO getTrackingHistory(Long id_tracking);
    List<TrackingHistoryDetailsDTO> getAllTrackingHistory(int page, int itens);
    TrackingHistoryDetailsDTO createTrackingHistory(TrackingHistoryCreateDTO dados);

}
