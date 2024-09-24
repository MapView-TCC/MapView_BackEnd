package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.serviceImp.LocationServiceImp;
import com.MapView.BackEnd.dtos.Location.LocationCreateDTO;
import com.MapView.BackEnd.dtos.Location.LocationDetalsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
@Tag(name = "Location", description = "Operations related to location management")
public class LocationController {

    public final LocationServiceImp locationServiceImp;

    public LocationController(LocationServiceImp locationServiceImp) {
        this.locationServiceImp = locationServiceImp;
    }


    @Operation(
            summary = "Create a new location",
            description = "Endpoint to create a new location in the system.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Location successfully created.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocationDetalsDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Data validation error."),
                    @ApiResponse(responseCode = "500", description = "Internal server error.")
            }
    )
    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173")
    @Transactional
    public ResponseEntity<LocationDetalsDTO> createLocation(LocationCreateDTO data, UriComponentsBuilder uriBuilder){
        //String email= jwt.getClaimAsString("email");
        var loc = locationServiceImp.createLocation(data);
        var uri  = uriBuilder.path("/ap1/v1/location/{id_location}").buildAndExpand(loc.id_location()).toUri();
        return ResponseEntity.created(uri).body(new LocationDetalsDTO(loc.id_location(),loc.post(),loc.enviroment()));
    }

    @Operation(
            summary = "Get all locations",
            description = "Retrieve a paginated list of all locations.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Locations successfully retrieved.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocationDetalsDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error.")
            }
    )
    @GetMapping
    public ResponseEntity<List<LocationDetalsDTO>> getAllLocations (@RequestParam int page, @RequestParam int itens){
        var loc = locationServiceImp.getAllLocation(page, itens);
        return ResponseEntity.ok(loc);
    }

    @Operation(
            summary = "Get location details",
            description = "Retrieve the details of a specific location by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Location found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocationDetalsDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Location not found.")
            }
    )
    @GetMapping("/{id_location}")
    public ResponseEntity<LocationDetalsDTO> getLocation(@PathVariable("id_location") Long id_location){
        var loc = locationServiceImp.getLocation(id_location);
        return ResponseEntity.ok(loc);

    }
}
