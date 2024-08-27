package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.repository.EnviromentRepository;
import com.MapView.BackEnd.repository.EquipmentRepository;
import com.MapView.BackEnd.repository.TrackingHistoryRepository;
import com.MapView.BackEnd.service.TrackingHistoryService;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryCreateDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryDetailsDTO;
import com.MapView.BackEnd.entities.Enviroment;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.TrackingHistory;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class TrackingHistoryServiceImp implements TrackingHistoryService {


    private final TrackingHistoryRepository trackingHistoryRepository;


    private final EnviromentRepository enviromentRepository;


    private final EquipmentRepository equipmentRepository;

    public TrackingHistoryServiceImp(TrackingHistoryRepository trackingHistoryRepository, EnviromentRepository enviromentRepository, EquipmentRepository equipmentRepository) {
        this.trackingHistoryRepository = trackingHistoryRepository;
        this.enviromentRepository = enviromentRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public TrackingHistoryDetailsDTO getTrackingHistory(Long id_tracking) {
        TrackingHistory trackingHistory = this.trackingHistoryRepository.findById(id_tracking)
                .orElseThrow(() -> new NotFoundException("Id not found"));

        return new TrackingHistoryDetailsDTO(trackingHistory);
    }

    @Override
    public List<TrackingHistoryDetailsDTO> getAllTrackingHistory(int page, int itens) {
        return trackingHistoryRepository.findAll(PageRequest.of(page, itens)).stream().map(TrackingHistoryDetailsDTO::new).toList();
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
