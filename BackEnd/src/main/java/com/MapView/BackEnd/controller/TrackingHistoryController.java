package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.dtos.TrackingHistory.TrackHistoryByenvironmentDetailsDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryWrongLocationDTO;
import com.MapView.BackEnd.enums.EnumWarnings;
import com.MapView.BackEnd.enums.EnumTrackingAction;
import com.MapView.BackEnd.serviceImp.TrackingHistoryServiceImp;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryCreateDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryDetailsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trackingHistory")
@Tag(name = "Tracking Historic", description = "Operations related to tracking history management")
public class TrackingHistoryController {

    private final TrackingHistoryServiceImp trackingHistoryServiceImp;

    public TrackingHistoryController(TrackingHistoryServiceImp trackingHistoryServiceImp) {
        this.trackingHistoryServiceImp = trackingHistoryServiceImp;
    }

    @Operation(summary = "Create a new tracking history", description = "Endpoint to create a new tracking record in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tracking record successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @Transactional
    public TrackingHistoryDetailsDTO createTracking(
            @Parameter(description = "Data transfer object for creating a new tracking history", required = true)
            @RequestBody @Valid TrackingHistoryCreateDTO dados,
            UriComponentsBuilder uriBuilder) {
        var tracking = trackingHistoryServiceImp.createTrackingHistory(dados);

        var uri = uriBuilder.path("/api/v1/trackingHistory/{id}").buildAndExpand(tracking.id_tracking()).toUri();
        return ResponseEntity.created(uri)
                .body(new TrackingHistoryDetailsDTO(tracking.id_tracking(), tracking.datetime(), tracking.equipment(), tracking.environment(), tracking.action(), tracking.warning()))
                .getBody();
    }

    @Operation(summary = "Get tracking history by ID", description = "Retrieve tracking history details by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tracking history details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Tracking history not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TrackingHistoryDetailsDTO> getIdTracking(
            @Parameter(description = "ID of the tracking history to retrieve", required = true)
            @PathVariable Long id) {
        var tracking = trackingHistoryServiceImp.getTrackingHistory(id);
        return ResponseEntity.ok(tracking);
    }

    @Operation(summary = "Get all tracking history", description = "Retrieve a paginated list of all tracking history records.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tracking history records retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<TrackingHistoryDetailsDTO>> getAllTracking() {
        var list = trackingHistoryServiceImp.getAllTrackingHistory();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Filter tracking history", description = "Retrieve tracking history records with optional filters like action, date, color, and equipment ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtered tracking history records retrieved successfully")
    })
    @GetMapping("/filter")
    public ResponseEntity<List<TrackingHistoryDetailsDTO>> getAllTrackingFilter(
            @Parameter(description = "Page number for pagination", required = true)
            @RequestParam int page,
            @Parameter(description = "Number of items per page", required = true)
            @RequestParam int itens,
            @Parameter(description = "Optional action filter")
            @RequestParam(required = false) EnumTrackingAction action,
            @Parameter(description = "Optional day filter")
            @RequestParam(required = false) Integer day,
            @Parameter(description = "Optional month filter")
            @RequestParam(required = false) Integer month,
            @Parameter(description = "Optional year filter")
            @RequestParam(required = false) Integer year,
            @Parameter(description = "Optional color filter")
            @RequestParam(required = false) EnumWarnings colors,
            @Parameter(description = "Optional equipment ID filter")
            @RequestParam(required = false) String id_equipment) {
        var list = trackingHistoryServiceImp.FilterTracking(page, itens, action, day, month, year, colors, id_equipment);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/filterbyenvironment")
    public ResponseEntity<List<TrackHistoryByenvironmentDetailsDTO>> filterbyenvironment(@RequestParam Long id_environment){
       return  ResponseEntity.ok( trackingHistoryServiceImp.getLatestTrackingHistoryByEnvironmentWithActionEntre(id_environment));

    }

    @Operation(summary = "Permanently delete tracking history", description = "Deleting tracking history records.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tracking history successfully deleted")
    })
    @DeleteMapping("/{id_tracking}")
    public ResponseEntity<Void> deleteTracking(@PathVariable("id_tracking") Long id_tracking){
        trackingHistoryServiceImp.deleteTrackingById(id_tracking);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Equipment in the wrong place", description = "Returning equipment that is not in the original laboratory.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment located in the wrong place")
    })
    @GetMapping("/wronglocations")
    public ResponseEntity<List<TrackingHistoryWrongLocationDTO>> getWrongLocationEquipment(@RequestParam("id_environment") Long id_environment){
        List<TrackingHistoryWrongLocationDTO> equipment =  trackingHistoryServiceImp.findWrongLocationEquipments(id_environment);
        return ResponseEntity.ok(equipment);

    }
}
