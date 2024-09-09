package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.enums.EnumColors;
import com.MapView.BackEnd.enums.EnumTrackingAction;
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

        var uri = uriBuilder.path("/api/v1/trackingHistory/{id}").buildAndExpand(tracking.id_tracking()).toUri();
        return ResponseEntity.created(uri).body(new TrackingHistoryDetailsDTO(tracking.id_tracking(), tracking.datetime(), tracking.equipment(), tracking.environment(), tracking.action(), tracking.warning())).getBody();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackingHistoryDetailsDTO> getIdTracking(@PathVariable Long id){
        var tracking = trackingHistoryServiceImp.getTrackingHistory(id);
        return ResponseEntity.ok(tracking);
    }

    @GetMapping
    public ResponseEntity<List<TrackingHistoryDetailsDTO>> getAllTracking(@RequestParam int page, @RequestParam int itens){
        var list = trackingHistoryServiceImp.getAllTrackingHistory(page,itens);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TrackingHistoryDetailsDTO>> getAllTrackingFilter(@RequestParam int page, @RequestParam int itens, @RequestParam(required = false)EnumTrackingAction action,
                                                                                @RequestParam(required = false)Integer day, @RequestParam(required = false)Integer month, @RequestParam(required = false)Integer year,
                                                                                @RequestParam(required = false)EnumColors colors, @RequestParam(required = false)String id_equipment){
        var list = trackingHistoryServiceImp.FilterTracking(page, itens, action, day, month, year, colors, id_equipment);
        return ResponseEntity.ok(list);
    }
}
