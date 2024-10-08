package com.MapView.BackEnd.controller;


import com.MapView.BackEnd.serviceImp.EnvironmentServiceImp;
import com.MapView.BackEnd.dtos.Environment.EnvironmentCreateDTO;
import com.MapView.BackEnd.dtos.Environment.EnvironmentDetailsDTO;
import com.MapView.BackEnd.dtos.Environment.EnvironmentUpdateDTO;
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
@RequestMapping("/api/v1/environment")
@Tag(name = "Environment", description = "Operations related to environment management")
public class EnvironmentController {

    private final EnvironmentServiceImp environmentServiceImp;

    public EnvironmentController(EnvironmentServiceImp environmentServiceImp) {
        this.environmentServiceImp = environmentServiceImp;
    }

    @Operation(
            summary = "Create a new environment.",
            description = "Endpoint to create a new environment in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Environment successfully created.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnvironmentDetailsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Data validation error."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<EnvironmentDetailsDTO> createEnvironment(
            @Parameter(description = "Data transfer object for creating a new environment", required = true)
            @RequestBody @Valid EnvironmentCreateDTO data,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id,
            UriComponentsBuilder uriBuilder) {
        var environment = environmentServiceImp.createEnvironment(data, userLog_id);
        var uri = uriBuilder.path("/api/v1/environment/{id}").buildAndExpand(environment.id_environment()).toUri();
        return ResponseEntity.created(uri).body(new EnvironmentDetailsDTO(environment.id_environment(), environment.environment_name(), environment.raspberry()));
    }

    @Operation(
            summary = "Update environment details.",
            description = "Endpoint to update an existing environment by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Environment successfully updated.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnvironmentDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Environment not found.")
    })
    @PutMapping("{id_environment}")
    @Transactional
    public ResponseEntity<EnvironmentDetailsDTO> updateEnvironment(
            @Parameter(description = "The ID of the environment to be updated", required = true)
            @PathVariable("id_environment") Long id_environment,
            @Parameter(description = "DTO for updating the environment", required = true)
            @RequestBody @Valid EnvironmentUpdateDTO data,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        var environment = environmentServiceImp.updateEnvironment(id_environment, data, userLog_id);
        return ResponseEntity.ok(environment);
    }

    @Operation(
            summary = "Get environment details.",
            description = "Retrieve the details of an environment by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Environment found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnvironmentDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Environment not found.")
    })
    @GetMapping("/{id_environment}")
    public ResponseEntity<EnvironmentDetailsDTO> getEnvironment(
            @Parameter(description = "The ID of the environment to be retrieved", required = true)
            @PathVariable("id_environment") Long id_environment,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        var environment = environmentServiceImp.getEnvironment(id_environment, userLog_id);
        return ResponseEntity.ok(environment);
    }

    @Operation(
            summary = "Get all environments.",
            description = "Retrieve a paginated list of all environments."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Environments successfully retrieved.")
    })
    @GetMapping
    public ResponseEntity<List<EnvironmentDetailsDTO>> getAllEnvironment(
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        var environment = environmentServiceImp.getAllEnvironment(userLog_id);
        return ResponseEntity.ok(environment);
    }

    @Operation(
            summary = "Activate environment.",
            description = "Activate an inactive environment by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Environment successfully activated."),
            @ApiResponse(responseCode = "404", description = "Environment not found.")
    })
    @PutMapping("/active/{id_environment}")
    @Transactional
    public ResponseEntity<Void> activeEnvironment(
            @Parameter(description = "The ID of the environment to be activated", required = true)
            @PathVariable("id_environment") Long id_environment,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        environmentServiceImp.activateEnvironment(id_environment, userLog_id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Inactivate environment.",
            description = "Inactivate an active environment by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Environment successfully inactivated."),
            @ApiResponse(responseCode = "404", description = "Environment not found.")
    })
    @PutMapping("/inactivate/{id_environment}")
    @Transactional
    public ResponseEntity<Void> inactiveEnvironment(
            @Parameter(description = "The ID of the environment to be inactivated", required = true)
            @PathVariable("id_environment") Long id_environment,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        environmentServiceImp.inactivateEnvironment(id_environment, userLog_id);
        return ResponseEntity.ok().build();
    }
}
