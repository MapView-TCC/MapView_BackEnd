package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.dtos.Equipment.*;
import com.MapView.BackEnd.dtos.ImageUpload.UploadCreateDTO;
import com.MapView.BackEnd.enums.EnumModelEquipment;
import com.MapView.BackEnd.serviceImp.EquipmentServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipment")
@Tag(name = "Equipment", description = "Operations related to equipment management")
public class EquipmentController {

    private final EquipmentServiceImp equipmentServiceImp;

    public EquipmentController(EquipmentServiceImp equipmentServiceImp) {
        this.equipmentServiceImp = equipmentServiceImp;
    }

    @Operation(summary = "Create a new equipment", description = "Endpoint to create a new equipment in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Equipment successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EquipmentDetailsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Data validation error"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<EquipmentDetailsDTO> createEquipament(
            @Parameter(description = "Data transfer object for creating a new equipment", required = true)
            @RequestBody @Valid EquipmentCreateDTO dados, UriComponentsBuilder uriBuilder,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {

        var equipment = equipmentServiceImp.createEquipment(dados, userLog_id);
        var uri = uriBuilder.path("/api/v1/equipment/{id}").buildAndExpand(equipment.id_equipment()).toUri();
        return ResponseEntity.created(uri).body(new EquipmentDetailsDTO(
                equipment.id_equipment(), equipment.code(), equipment.name_equipment(), equipment.rfid(), equipment.type(),
                equipment.model(), equipment.validity(), equipment.admin_rights(), equipment.observation(),
                equipment.location(), equipment.owner()
        ));
    }

    @Operation(summary = "Retrieve all equipment", description = "Get a paginated list of all equipment in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment list successfully retrieved")
    })
    @GetMapping
    public ResponseEntity<List<EquipmentDetailsDTO>> getAllEquipment(
            @Parameter(description = "Page number for pagination", required = false) @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", required = false) @RequestParam(defaultValue = "200") int itens,
            @Parameter(description = "User log ID for tracking changes", required = true) @RequestParam Long userLog_id) {
        var list = equipmentServiceImp.getAllEquipment(page, itens, userLog_id);
        return ResponseEntity.ok(list);
    }

    // barra de pesquisa
    @Operation(summary = "Filter equipment search bar", description = "Retrieve equipment with search bar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtered equipment list successfully retrieved")
    })
    @GetMapping("/search")
    public ResponseEntity<List<EquipmentSearchBarDTO>> getEquipmentSearch(String searchTerm){
        var list = equipmentServiceImp.getEquipmentSearchBar(searchTerm);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Retrieve equipment by ID", description = "Get the details of an equipment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Equipment not found")
    })
    @GetMapping("/{code}")
    public ResponseEntity<EquipmentDetailsDTO> getById(
            @Parameter(description = "The ID of the equipment to retrieve", required = true)
            @PathVariable("code") String code,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        var equipment = equipmentServiceImp.getEquipmentCode(code, userLog_id);
        return ResponseEntity.ok(equipment);
    }

    @Operation(summary = "Inactivate equipment", description = "Inactivate an equipment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment successfully inactivated"),
            @ApiResponse(responseCode = "404", description = "Equipment not found")
    })
    @PutMapping("/inactivate/{code}")
    @Transactional
    public ResponseEntity<Void> inactivate(
            @Parameter(description = "The ID of the equipment to inactivate", required = true)
            @PathVariable String code,

            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        equipmentServiceImp.inactivateEquipment(code, userLog_id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Activate equipment", description = "Activate an equipment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment successfully activated"),
            @ApiResponse(responseCode = "404", description = "Equipment not found")
    })
    @PutMapping("/active/{code}")
    @Transactional
    public ResponseEntity<Void> active(
            @Parameter(description = "The ID of the equipment to activate", required = true)
            @PathVariable String code,

            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id) {
        equipmentServiceImp.activateEquipment(code, userLog_id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update equipment details", description = "Update the details of an equipment by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment successfully updated"),
            @ApiResponse(responseCode = "404", description = "Equipment not found")
    })
    @PutMapping("/{id_equipment}")
    @Transactional
    public ResponseEntity<EquipmentDetailsDTO> updateEquipment(
            @Parameter(description = "The ID of the equipment to update", required = true)
            @PathVariable Long id_equipment,
            @Parameter(description = "Data transfer object for updating the equipment", required = true)
            @RequestBody @Valid EquipmentUpdateDTO dados,
            @Parameter(description = "User log ID for tracking changes", required = true)
            @RequestParam Long userLog_id)
    {
        EquipmentDetailsDTO equipmentDetailsDTO = equipmentServiceImp.updateEquipment(id_equipment, dados, userLog_id);
        return ResponseEntity.ok(equipmentDetailsDTO);
    }

}
