package com.MapView.BackEnd.Service;

public interface TrackingHistoryService {
    void getTrackingHistoryService(Long id_TrackingHistoryService);
    void getAllTrackingHistoryService(String TrackingHistoryService);
    void createTrackingHistoryService(String TrackingHistoryService);
    void updateTrackingHistoryService(String TrackingHistoryService);
    void activeTrackingHistoryService(Long id_TrackingHistoryService);
    void inactivateEnviroment(Long id_TrackingHistoryService);
}
