package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.dtos.CostCenter.CostCenterCreateDTO;
import com.MapView.BackEnd.serviceImp.CostCenterServiceImp;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterDetailsDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterUpdateDTO;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
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
@RequestMapping("/api/v1/costcenter")
@Tag(name = "Cost Center", description = "Operations related to cost center management")
public class CostCenterController {

    private final CostCenterServiceImp costCenterServiceImp;

    public CostCenterController(CostCenterServiceImp costCenterServiceImp) {
        this.costCenterServiceImp = costCenterServiceImp;
    }

    @Operation(
            summary = "Create a new cost center.",
            description = "Endpoint to create a new cost center in the system.",
            externalDocs = @ExternalDocumentation(
                    description = "Learn more about cost center creation",
                    url = "https://example.com/costcenter/documentation"
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cost center successfully created.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CostCenterDetailsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Data validation error."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<CostCenterDetailsDTO> createCostCenter(
            @Parameter(description = "Data transfer object for creating a new cost center", required = true)
            @RequestBody @Valid CostCenterCreateDTO dados,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id,
            UriComponentsBuilder uriBuilder) {
        var costcenter = costCenterServiceImp.createCostCenter(dados, userLog_id);
        var uri = uriBuilder.path("/api/v1/costcenter/{id}").buildAndExpand(costcenter.id_cost_center()).toUri();
        return ResponseEntity.created(uri).body(new CostCenterDetailsDTO(costcenter.id_cost_center(), costcenter.costCenter()));
    }

    @Operation(summary = "Get all cost centers", description = "Retrieve a paginated list of all cost centers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cost centers successfully retrieved.")
    })
    @GetMapping
    public ResponseEntity<List<CostCenterDetailsDTO>> getAllCostCenter(
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        var list = costCenterServiceImp.getAllCostCenter(userLog_id);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get cost center details", description = "Retrieve the details of a cost center by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cost center found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CostCenterDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cost center not found.")
    })
    @GetMapping("/{id_costcenter}")
    public ResponseEntity<CostCenterDetailsDTO> getIdCostCenter(
            @Parameter(description = "The ID of the cost center to be retrieved", required = true)
            @PathVariable Long id_costcenter,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        var costCenter = costCenterServiceImp.getCostCenter(id_costcenter, userLog_id);
        return ResponseEntity.ok(costCenter);
    }

    @Operation(summary = "Update cost center", description = "Update the details of an existing cost center by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cost center successfully updated."),
            @ApiResponse(responseCode = "404", description = "Cost center not found.")
    })
    @PutMapping("{id_costcenter}")
    @Transactional
    public ResponseEntity<CostCenterDetailsDTO> updateCostCenter(
            @Parameter(description = "The ID of the cost center to be updated", required = true)
            @PathVariable Long id_costcenter,
            @Parameter(description = "DTO for updating the cost center", required = true)
            @RequestBody @Valid CostCenterUpdateDTO dados,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        CostCenterDetailsDTO updateCost = costCenterServiceImp.updateCostCenter(id_costcenter, dados, userLog_id);
        return ResponseEntity.ok(updateCost);
    }

    @Operation(summary = "Inactivate cost center", description = "Inactivate an active cost center by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cost center successfully inactivated."),
            @ApiResponse(responseCode = "404", description = "Cost center not found.")
    })
    @PutMapping("/inactivate/{id_costcenter}")
    @Transactional
    public ResponseEntity<Void> inactivate(
            @Parameter(description = "The ID of the cost center to be inactivated", required = true)
            @PathVariable Long id_costcenter,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        costCenterServiceImp.inactivateCostCenter(id_costcenter, userLog_id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Activate cost center", description = "Activate an inactive cost center by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cost center successfully activated."),
            @ApiResponse(responseCode = "404", description = "Cost center not found.")
    })
    @PutMapping("/active/{id_costcenter}")
    @Transactional
    public ResponseEntity<Void> active(
            @Parameter(description = "The ID of the cost center to be activated", required = true)
            @PathVariable Long id_costcenter,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        costCenterServiceImp.activateCostCenter(id_costcenter, userLog_id);
        return ResponseEntity.ok().build();
    }
}
