package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.serviceImp.RaspberryServiceImp;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryCreateDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryDetailsDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/api/v1/raspberry")
@Tag(name = "Raspberry", description = "Operations related to Raspberry management")
public class RaspberryController {

    private final RaspberryServiceImp raspberryServiceImp;

    public RaspberryController(RaspberryServiceImp raspberryServiceImp) {
        this.raspberryServiceImp = raspberryServiceImp;
    }

    @Operation(summary = "Create a new Raspberry", description = "Endpoint to create a new Raspberry in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Raspberry successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RaspberryDetailsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Data validation error"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<RaspberryDetailsDTO> createRaspberry(
            @Parameter(description = "Data transfer object for creating a new Raspberry", required = true)
            @RequestBody @Valid RaspberryCreateDTO raspberryCreateDTO,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id,
            UriComponentsBuilder uriBuilder) {
        var raspberry = raspberryServiceImp.createRaspberry(raspberryCreateDTO, userLog_id);
        var uri = uriBuilder.path("/api/v1/raspberry/{id}").buildAndExpand(raspberry.id_raspberry()).toUri();
        return ResponseEntity.created(uri).body(new RaspberryDetailsDTO(raspberry.id_raspberry(), raspberry.building(), raspberry.area()));
    }

    @Operation(summary = "Retrieve all Raspberries", description = "Get a paginated list of all Raspberries in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Raspberries list successfully retrieved")
    })
    @GetMapping
    public ResponseEntity<List<RaspberryDetailsDTO>> getAllRaspberry(
            @Parameter(description = "Page number for pagination", required = true)
            @RequestParam int page,
            @Parameter(description = "Number of items per page", required = true)
            @RequestParam int itens,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        var list = raspberryServiceImp.getAllRaspberry(page, itens, userLog_id);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Retrieve a Raspberry by ID", description = "Get the details of a Raspberry by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Raspberry successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Raspberry not found")
    })
    @GetMapping("/{id_raspberry}")
    public ResponseEntity<RaspberryDetailsDTO> getIdRaspberry(
            @Parameter(description = "The ID of the Raspberry to retrieve", required = true)
            @PathVariable String id_raspberry,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        RaspberryDetailsDTO raspberry = raspberryServiceImp.getRaspberry(id_raspberry, userLog_id);
        return ResponseEntity.ok(raspberry);
    }

    @Operation(summary = "Update a Raspberry", description = "Update the details of a Raspberry by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Raspberry successfully updated"),
            @ApiResponse(responseCode = "404", description = "Raspberry not found")
    })
    @PutMapping("/{id_raspberry}")
    @Transactional
    public ResponseEntity<RaspberryDetailsDTO> updateRaspberry(
            @Parameter(description = "The ID of the Raspberry to update", required = true)
            @PathVariable String id_raspberry,
            @Parameter(description = "Data transfer object for updating the Raspberry", required = true)
            @RequestBody @Valid RaspberryUpdateDTO dados,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        RaspberryDetailsDTO updateRaspberry = raspberryServiceImp.updateRaspberry(id_raspberry, dados, userLog_id);
        return ResponseEntity.ok(updateRaspberry);
    }

    @Operation(summary = "Inactivate a Raspberry", description = "Inactivate a Raspberry by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Raspberry successfully inactivated"),
            @ApiResponse(responseCode = "404", description = "Raspberry not found")
    })
    @PutMapping("/inactivate/{id_raspberry}")
    @Transactional
    public ResponseEntity<Void> inactivate(
            @Parameter(description = "The ID of the Raspberry to inactivate", required = true)
            @PathVariable String id_raspberry,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        raspberryServiceImp.inactivateRaspberry(id_raspberry, userLog_id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Activate a Raspberry", description = "Activate a Raspberry by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Raspberry successfully activated"),
            @ApiResponse(responseCode = "404", description = "Raspberry not found")
    })
    @PutMapping("/active/{id_raspberry}")
    @Transactional
    public ResponseEntity<Void> active(
            @Parameter(description = "The ID of the Raspberry to activate", required = true)
            @PathVariable String id_raspberry,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        raspberryServiceImp.activeRaspberry(id_raspberry, userLog_id);
        return ResponseEntity.ok().build();
    }
}
