package com.MapView.BackEnd.Controller;

import com.MapView.BackEnd.ServiceImp.TrackingHistoryServiceImp;
import com.MapView.BackEnd.dtos.AccessHistory.AccessHistoryDetailsDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryCreateDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryDetailsDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trackingHistory")
public class TrackingHistoryController {

    @Autowired
    private TrackingHistoryServiceImp trackingHistoryServiceImp;

    @PostMapping
    @Transactional
    public TrackingHistoryDetailsDTO create(@RequestBody TrackingHistoryCreateDTO dados, UriComponentsBuilder uriBuilder){
        var tracking = trackingHistoryServiceImp.createTrackingHistory(dados);

        var uri = uriBuilder.path("/api/v1/accessHistory/{id}").buildAndExpand(tracking.id_tracking()).toUri();
        return ResponseEntity.created(uri).body(new TrackingHistoryDetailsDTO(tracking.id_tracking(), tracking.dateTime(), tracking.id_equipment(), tracking.id_enviroment(), tracking.action())).getBody();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackingHistoryDetailsDTO> getId(@PathVariable Long id){
        var tracking = trackingHistoryServiceImp.getTrackingHistory(id);
        return ResponseEntity.ok(tracking);
    }

    @GetMapping
    public ResponseEntity<List<TrackingHistoryDetailsDTO>> getAll(){
        var list = trackingHistoryServiceImp.getAllTrackingHistory();
        return ResponseEntity.ok(list);
    }
}
