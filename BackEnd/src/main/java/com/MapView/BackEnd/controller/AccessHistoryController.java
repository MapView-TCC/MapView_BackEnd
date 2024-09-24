package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.serviceImp.AccessHistoryServiceImp;
import com.MapView.BackEnd.dtos.AccessHistory.AccessHistoryCreateDTO;
import com.MapView.BackEnd.dtos.AccessHistory.AccessHistoryDetailsDTO;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accessHistory")
@Tag(name = "Access History", description = "Operations related to access history management")
public class AccessHistoryController {


    private final AccessHistoryServiceImp accessHistoryServiceImp;

    public AccessHistoryController(AccessHistoryServiceImp accessHistoryServiceImp) {
        this.accessHistoryServiceImp = accessHistoryServiceImp;
    }


    @Operation(
            summary = "Create a new access history.",
            description = "Endpoint to create a new access history record in the system.",
            externalDocs = @ExternalDocumentation(
                    description = "Learn more about access history creation",
                    url = "https://example.com/accesshistory/documentation"
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Access history successfully created.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccessHistoryDetailsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Data validation error."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping
    @Transactional
    public AccessHistoryDetailsDTO createAccessHistory(@RequestBody @Valid AccessHistoryCreateDTO accessHistoryCreateDTO, UriComponentsBuilder uriBuilder){
        var history = accessHistoryServiceImp.createAccessHistory(accessHistoryCreateDTO);

        // boa pratica, para retornar o caminho
        var uri = uriBuilder.path("/api/v1/accessHistory/{id}").buildAndExpand(history.id_history()).toUri();
        return ResponseEntity.created(uri).body(new AccessHistoryDetailsDTO(history.id_history(), history.users(), history.login_dateTime(), history.logout_dateTime())).getBody();
    }


    @Operation(summary = "Update access history", description = "Update the details of an existing access history by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access history successfully updated."),
            @ApiResponse(responseCode = "404", description = "Access history not found.")
    })
    @PutMapping("/{id_access_history}")
    @Transactional
    public ResponseEntity<AccessHistoryDetailsDTO> updateAccessHistory(@PathVariable Long id_access_history){
        AccessHistoryDetailsDTO historyDetailsDTO = accessHistoryServiceImp.updateAccessHistory(id_access_history);
        return ResponseEntity.ok(historyDetailsDTO);
    }

    @Operation(summary = "Get access history details", description = "Retrieve the details of an access history by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access history found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccessHistoryDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Access history not found.")
    })
    @GetMapping("/{id_access_history}")
    public ResponseEntity<AccessHistoryDetailsDTO> getAccessHistory(@PathVariable Long id_access_history){
        var accessHistory = accessHistoryServiceImp.getAccessHistory(id_access_history);
        return ResponseEntity.ok(accessHistory);
    }

    @Operation(summary = "Get all access histories", description = "Retrieve a paginated list of all access histories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access histories successfully retrieved.")
    })
    @GetMapping
    public ResponseEntity<List<AccessHistoryDetailsDTO>> getAllAccessHistory(@RequestParam int page, @RequestParam int itens){
        var list = accessHistoryServiceImp.getAllAccessHistory(page,itens);
        return ResponseEntity.ok(list);
    }
}
