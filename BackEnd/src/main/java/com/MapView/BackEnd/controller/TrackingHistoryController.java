package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.serviceImp.TrackingHistoryServiceImp;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryCreateDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryDetailsDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trackingHistory")
public class TrackingHistoryController {


    private final TrackingHistoryServiceImp trackingHistoryServiceImp;

    public TrackingHistoryController(TrackingHistoryServiceImp trackingHistoryServiceImp) {
        this.trackingHistoryServiceImp = trackingHistoryServiceImp;
    }

    @PostMapping
    @Transactional
    public TrackingHistoryDetailsDTO createTracking(@RequestBody TrackingHistoryCreateDTO dados, UriComponentsBuilder uriBuilder){
        var tracking = trackingHistoryServiceImp.createTrackingHistory(dados);

        var uri = uriBuilder.path("/api/v1/accessHistory/{id}").buildAndExpand(tracking.id_tracking()).toUri();
        return ResponseEntity.created(uri).body(new TrackingHistoryDetailsDTO(tracking.id_tracking(), tracking.dateTime(), tracking.id_equipment(), tracking.id_enviroment(), tracking.action())).getBody();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackingHistoryDetailsDTO> getIdTracking(@PathVariable Long id){
        var tracking = trackingHistoryServiceImp.getTrackingHistory(id);
        return ResponseEntity.ok(tracking);
    }

    @GetMapping
    public ResponseEntity<List<TrackingHistoryDetailsDTO>> getAllTracking(){
        var list = trackingHistoryServiceImp.getAllTrackingHistory();
        return ResponseEntity.ok(list);
    }
}
