package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.service.UserLogService;

import com.MapView.BackEnd.dtos.UserLog.UserLogDetailsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/userlog")
@Tag(name = "User Log", description = "Operations related to user log management")
public class UserLogController {

    private final UserLogService userLogService;

    public UserLogController(UserLogService userLogRepository, UserLogService userLogService) {
        this.userLogService = userLogService;
    }


    @Operation(
            summary = "Get user log details",
            description = "Retrieve the details of a specific user log by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User log found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserLogDetailsDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User log not found.")
            }
    )
    @GetMapping("/{userlog_id}")
    public UserLogDetailsDTO getUserLog(@PathVariable("userlog_id") Long userlog_id){
        var userlog = userLogService.getUserLog(userlog_id);
        return ResponseEntity.ok(userlog).getBody();
    }

    @Operation(
            summary = "Get all user logs",
            description = "Retrieve a paginated list of all user logs.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User logs successfully retrieved.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserLogDetailsDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error.")
            }
    )
    @GetMapping
    public ResponseEntity<List<UserLogDetailsDTO>> getAllUser(){
        var user = userLogService.getAllUserLog();
        return ResponseEntity.ok(user);
    }
}
