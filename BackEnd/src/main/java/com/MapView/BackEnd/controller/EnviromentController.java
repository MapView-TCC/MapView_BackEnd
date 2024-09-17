package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.serviceImp.EnviromentServiceImp;
import com.MapView.BackEnd.dtos.Enviroment.EnviromentCreateDTO;
import com.MapView.BackEnd.dtos.Enviroment.EnviromentDetailsDTO;
import com.MapView.BackEnd.dtos.Enviroment.EnviromentUpdateDTO;
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
@RequestMapping("/api/v1/enviroment")
@Tag(name = "Enviroment", description = "Operations related to environment management")
public class EnviromentController {

    private final EnviromentServiceImp enviromentServiceImp;

    public EnviromentController(EnviromentServiceImp enviromentServiceImp) {
        this.enviromentServiceImp = enviromentServiceImp;
    }

    @Operation(
            summary = "Create a new environment.",
            description = "Endpoint to create a new environment in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Environment successfully created.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnviromentDetailsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Data validation error."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<EnviromentDetailsDTO> createEnviroment(
            @Parameter(description = "Data transfer object for creating a new environment", required = true)
            @RequestBody @Valid EnviromentCreateDTO data,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id,
            UriComponentsBuilder uriBuilder) {
        var enviroment = enviromentServiceImp.createEnviroment(data, userLog_id);
        var uri = uriBuilder.path("/api/v1/enviroment/{id}").buildAndExpand(enviroment.id_enviroment()).toUri();
        return ResponseEntity.created(uri).body(new EnviromentDetailsDTO(enviroment.id_enviroment(), enviroment.environment_name(), enviroment.raspberry()));
    }

    @Operation(
            summary = "Update environment details.",
            description = "Endpoint to update an existing environment by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Environment successfully updated.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnviromentDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Environment not found.")
    })
    @PutMapping("{id_enviroment}")
    @Transactional
    public ResponseEntity<EnviromentDetailsDTO> updateEnviroment(
            @Parameter(description = "The ID of the environment to be updated", required = true)
            @PathVariable("id_enviroment") Long id_enviroment,
            @Parameter(description = "DTO for updating the environment", required = true)
            @RequestBody @Valid EnviromentUpdateDTO data,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        var enviroment = enviromentServiceImp.updateEnviroment(id_enviroment, data, userLog_id);
        return ResponseEntity.ok(enviroment);
    }

    @Operation(
            summary = "Get environment details.",
            description = "Retrieve the details of an environment by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Environment found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnviromentDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Environment not found.")
    })
    @GetMapping("/{id_enviroment}")
    public ResponseEntity<EnviromentDetailsDTO> getEnviroment(
            @Parameter(description = "The ID of the environment to be retrieved", required = true)
            @PathVariable("id_enviroment") Long id_enviroment,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        var enviroment = enviromentServiceImp.getEnviroment(id_enviroment, userLog_id);
        return ResponseEntity.ok(enviroment);
    }

    @Operation(
            summary = "Get all environments.",
            description = "Retrieve a paginated list of all environments."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Environments successfully retrieved.")
    })
    @GetMapping
    public ResponseEntity<List<EnviromentDetailsDTO>> getAllEnviroment(
            @Parameter(description = "Page number for pagination", required = true)
            @RequestParam int page,
            @Parameter(description = "Number of items per page", required = true)
            @RequestParam int itens,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        var enviroment = enviromentServiceImp.getAllEnviroment(page, itens, userLog_id);
        return ResponseEntity.ok(enviroment);
    }

    @Operation(
            summary = "Activate environment.",
            description = "Activate an inactive environment by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Environment successfully activated."),
            @ApiResponse(responseCode = "404", description = "Environment not found.")
    })
    @PutMapping("/active/{id_enviroment}")
    @Transactional
    public ResponseEntity<Void> activeEnviroment(
            @Parameter(description = "The ID of the environment to be activated", required = true)
            @PathVariable("id_enviroment") Long id_enviroment,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        enviromentServiceImp.activateEnviroment(id_enviroment, userLog_id);
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
    @PutMapping("/inactivate/{id_enviroment}")
    @Transactional
    public ResponseEntity<Void> inactiveEnviroment(
            @Parameter(description = "The ID of the environment to be inactivated", required = true)
            @PathVariable("id_enviroment") Long id_enviroment,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        enviromentServiceImp.inactivateEnviroment(id_enviroment, userLog_id);
        return ResponseEntity.ok().build();
    }
}
