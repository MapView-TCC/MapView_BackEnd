package com.MapView.BackEnd.ServiceImp;

import com.MapView.BackEnd.Repository.EnviromentRepository;
import com.MapView.BackEnd.Repository.EquipmentRepository;
import com.MapView.BackEnd.Repository.TrackingHistoryRepository;
import com.MapView.BackEnd.Service.TrackingHistoryService;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryCreateDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryDetailsDTO;
import com.MapView.BackEnd.entities.Enviroment;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.TrackingHistory;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackingHistoryServiceImp implements TrackingHistoryService {

    @Autowired
    private TrackingHistoryRepository trackingHistoryRepository;

    @Autowired
    private EnviromentRepository enviromentRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public TrackingHistoryDetailsDTO getTrackingHistory(Long id_tracking) {
        TrackingHistory trackingHistory = this.trackingHistoryRepository.findById(id_tracking)
                .orElseThrow(() -> new NotFoundException("Id not found"));

        return new TrackingHistoryDetailsDTO(trackingHistory);
    }

    @Override
    public List<TrackingHistoryDetailsDTO> getAllTrackingHistory() {
        return trackingHistoryRepository.findAll().stream().map(TrackingHistoryDetailsDTO::new).toList();
    }

    @Override
    public TrackingHistoryDetailsDTO createTrackingHistory(TrackingHistoryCreateDTO dados) {
        Equipment equipment = equipmentRepository.findById(dados.id_equipment())
                .orElseThrow(() -> new RuntimeException("Id not found"));

        Enviroment enviroment = enviromentRepository.findById(dados.id_enviroment())
                .orElseThrow(() -> new RuntimeException("Id not found"));

        TrackingHistory trackingHistory = new TrackingHistory();
        trackingHistory.setId_enviroment(enviroment);
        trackingHistory.setId_equipment(equipment);

        trackingHistoryRepository.save(trackingHistory);

        return new TrackingHistoryDetailsDTO(trackingHistory);
    }
}
