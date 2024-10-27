package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.dtos.CostCenter.CostCenterDetailsDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleSearchDetailsDTO;
import com.MapView.BackEnd.serviceImp.EquipmentResponsibleServiceImp;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleCreateDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleDetailsDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleUpdateDTO;
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
@RequestMapping("/api/v1/equipmentresponsible")
@Tag(name = "Equipment Responsible", description = "Operations related to equipment responsible management")
public class EquipmentResponsibleController {


    private final EquipmentResponsibleServiceImp equipmentResponsibleServiceImp;

    public EquipmentResponsibleController(EquipmentResponsibleServiceImp equipmentResponsibleServiceImp) {
        this.equipmentResponsibleServiceImp = equipmentResponsibleServiceImp;
    }

    @Operation(summary = "Get all equipment responsible", description = "Retrieve a paginated list of all equipment responsible.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment responsible successfully retrieved.")
    })
    @GetMapping
    public ResponseEntity<List<EquipmentResponsibleDetailsDTO>> getAllEquipmentResponsible(){
        var list = equipmentResponsibleServiceImp.getAllEquipmentResponsible();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get equipment responsible details", description = "Retrieve the details of a equipment responsible by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment Responsible found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EquipmentResponsibleDetailsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Equipment Responsible not found.")
    })
    @GetMapping("/{id_equip_resp}")
    public ResponseEntity<EquipmentResponsibleDetailsDTO> getEquipmentResponsible(@PathVariable Long id_equip_resp){
        EquipmentResponsibleDetailsDTO equipment = equipmentResponsibleServiceImp.getEquipmentResponsible(id_equip_resp);
        return ResponseEntity.ok(equipment);
    }


    @Operation(
            summary = "Create a new equipment responsible.",
            description = "Endpoint to create a new equipment responsible in the system.",
            externalDocs = @ExternalDocumentation(
                    description = "Learn more about equipment responsible creation",
                    url = "https://example.com/equipmentresponsible/documentation"
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Equipment Responsible successfully created.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EquipmentResponsibleDetailsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Data validation error."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<EquipmentResponsibleDetailsDTO> createEquipmentResponsible(@RequestBody @Valid EquipmentResponsibleCreateDTO equipmentResponsibleCreateDTO, UriComponentsBuilder uriBuilder){
        var equipmentResponsible = equipmentResponsibleServiceImp.createEquipmentResponsible(equipmentResponsibleCreateDTO);

        // boa pratica, para retornar o caminho
        var uri = uriBuilder.path("/api/v1/equipmentresponsible/{id}").buildAndExpand(equipmentResponsible.id_equip_resp()).toUri();
        return ResponseEntity.created(uri).body(new EquipmentResponsibleDetailsDTO(equipmentResponsible.id_equip_resp(), equipmentResponsible.equipment(), equipmentResponsible.responsible(), equipmentResponsible.start_usage(), equipmentResponsible.end_usage()));
    }

    @Operation(summary = "Update equipment responsible", description = "Update the details of an existing equipment responsible by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment responsible successfully updated."),
            @ApiResponse(responseCode = "404", description = "Equipment responsible not found.")
    })
    @PutMapping("/{id_equip_resp}")
    @Transactional
    public ResponseEntity<EquipmentResponsibleDetailsDTO> updateEquipmentResponsible(@PathVariable Long id_equip_resp, @RequestBody @Valid EquipmentResponsibleUpdateDTO dados){
        EquipmentResponsibleDetailsDTO updateEquipmentResponsible = equipmentResponsibleServiceImp.updateEquipmentResponsible(id_equip_resp, dados);
        return ResponseEntity.ok(updateEquipmentResponsible);
    }


    @Operation(summary = "Inactivate equipment responsible", description = "Inactivate an active equipment responsible by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment responsible successfully inactivated."),
            @ApiResponse(responseCode = "404", description = "Equipment responsible not found.")
    })
    @PutMapping("/inactivate/{id_equip_resp}")
    @Transactional
    public ResponseEntity<Void> inactivate(@PathVariable Long id_equip_resp){
        equipmentResponsibleServiceImp.inactivateEquipmentResponsible(id_equip_resp);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Activate equipment responsible", description = "Activate an inactive equipment responsible by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment responsible successfully activated."),
            @ApiResponse(responseCode = "404", description = "Equipment responsible not found.")
    })
    @PutMapping("/active/{id_equip_resp}")
    @Transactional
    public ResponseEntity<Void> active(@PathVariable Long id_equip_resp){
        equipmentResponsibleServiceImp.activateEquipmentResponsible(id_equip_resp);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Filter equipment", description = "Retrieve equipment with optional filters for validity, environment, owner, etc.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtered equipment list successfully retrieved")
    })
    @GetMapping("/filter")
    public ResponseEntity<EquipmentResponsibleSearchDetailsDTO> getAllEquipmentFilter(

            @RequestParam(required = false) String id_equipment
   ) {
        var list = equipmentResponsibleServiceImp.getEquipmentInventory( id_equipment );
        return ResponseEntity.ok(list);
    }
}


