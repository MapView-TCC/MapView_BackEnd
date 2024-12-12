package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.dtos.User.UserCreateDTO;
import com.MapView.BackEnd.serviceImp.UserServiceImp;
import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import com.MapView.BackEnd.dtos.User.UserUpdateDTO;
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
@RequestMapping("ap1/v1/user")
@Tag(name = "User", description = "Operations related to user management")
public class UserController {

    private final UserServiceImp userServiceImp;

    public UserController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @Operation(summary = "Create a new user", description = "Endpoint to create a new user in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173")
    @Transactional
    public ResponseEntity<UserDetailsDTO> createUser(
            @Parameter(description = "Data transfer object for creating a new user", required = true)
            @RequestBody @Valid UserCreateDTO data,
            UriComponentsBuilder uriBuilder) {
        UserDetailsDTO user = userServiceImp.createUser(data);
        var uri = uriBuilder.path("/user/{id}").buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(uri).body(new UserDetailsDTO(user.id(), user.email(), user.roleUser()));
    }

    @Operation(summary = "Set user privileges", description = "Update the role or privilege of a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Privilege successfully updated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{user_id}")
    @Transactional
    public ResponseEntity<Void> setPrivilege(
            @Parameter(description = "User role update DTO", required = true)
            @RequestBody @Valid UserUpdateDTO data,
            @Parameter(description = "User ID to update", required = true)
            @PathVariable("user_id") Long user_id) {
        userServiceImp.setPrivilege(user_id, data.roleUser());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get user by ID", description = "Retrieve user details by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{user_id}")
    public ResponseEntity<UserDetailsDTO> getUser(
            @Parameter(description = "User ID to retrieve", required = true)
            @PathVariable("user_id") Long user_id) {
        var user = userServiceImp.getUser(user_id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get all users", description = "Retrieve a paginated list of all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> getAllUser() {
        var users = userServiceImp.getAllUser();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Activate a user", description = "Activate a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully activated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/active/{user_id}")
    @Transactional
    public ResponseEntity<Void> activeUser(
            @Parameter(description = "User ID to activate", required = true)
            @PathVariable("user_id") Long user_id) {
        userServiceImp.activeUser(user_id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Inactivate a user", description = "Inactivate a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully inactivated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/inactivate/{user_id}")
    @Transactional
    public ResponseEntity<Void> inactiveUser(
            @Parameter(description = "User ID to inactivate", required = true)
            @PathVariable("user_id") Long user_id) {
        userServiceImp.inactivateUser(user_id);
        return ResponseEntity.ok().build();
    }
}
