package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.dtos.Token.Token;
import com.MapView.BackEnd.dtos.Token.TokenDetailsDTO;
import com.MapView.BackEnd.dtos.User.LoggedUserDetails;
import com.MapView.BackEnd.serviceImp.UserServiceImp;
import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@Tag(name = "User", description = "Operations related to user management")
public class UserController {

    private final UserServiceImp userServiceIpm;

    public UserController(UserServiceImp userServiceIpm) {
        this.userServiceIpm = userServiceIpm;
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
        var user = userServiceIpm.getUser(user_id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get all users", description = "Retrieve a paginated list of all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> getAllUser() {
        var users = userServiceIpm.getAllUser();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/credentials")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<TokenDetailsDTO> getCredentials(@AuthenticationPrincipal Jwt jwt){
        Token credentials = userServiceIpm.getCredencials(jwt);
        System.out.println(credentials);
        return ResponseEntity.ok(new TokenDetailsDTO(credentials));

    }
    @GetMapping("/loggedUser")
    public LoggedUserDetails loggedUser(@AuthenticationPrincipal Jwt jwt){
        return new LoggedUserDetails(this.userServiceIpm.loggedUser(jwt));
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
        userServiceIpm.activeUser(user_id);
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
        userServiceIpm.inactivateUser(user_id);
        return ResponseEntity.ok().build();
    }
}
