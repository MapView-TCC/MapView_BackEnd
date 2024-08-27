package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.serviceImp.UserServiceIpm;
import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import com.MapView.BackEnd.dtos.User.UserUpdateDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("ap1/v1/user")

public class UserController {

    private final UserServiceIpm userServiceIpm;

    public UserController(UserServiceIpm userServiceIpm) {
        this.userServiceIpm = userServiceIpm;
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:5173")
    @Transactional
    public ResponseEntity<UserDetailsDTO> createUser(String email, UriComponentsBuilder uriBuilder){
        //String email= jwt.getClaimAsString("email");
        UserDetailsDTO user = userServiceIpm.createUser(email);
        var uri  = uriBuilder.path("/user/{id}").buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(uri).body(new UserDetailsDTO(user.id(), user.email(), user.roleUser()));
    }

    @PostMapping("/{user_id}")
    @Transactional
    public ResponseEntity<Void> setPrivilege(@RequestBody UserUpdateDTO data, @PathVariable("user_id") Long user_id){
        userServiceIpm.setPrivilege(user_id,data.roleUser());
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{user_id}")
    public ResponseEntity<UserDetailsDTO> getUser(@PathVariable("user_id") Long user_id){
        var user = userServiceIpm.getUser(user_id);
        return ResponseEntity.ok(user);

    }

    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> getAllUser(){
        var user = userServiceIpm.getAllUser();
        return ResponseEntity.ok(user);

    }
    @PutMapping("/active/{user_id}")
    @Transactional
    public ResponseEntity<Void> activeUser(@PathVariable("user_id") Long user_id){
        userServiceIpm.activeUser(user_id);
        return ResponseEntity.ok().build();

    }
    @PutMapping("/inactivate/{user_id}")
    @Transactional
    public ResponseEntity<Void> inactiveUser(@PathVariable("user_id") Long user_id){
        userServiceIpm.inactivateUser(user_id);
        return ResponseEntity.ok().build();

    }



}
