package com.MapView.BackEnd.Controller;

import com.MapView.BackEnd.ServiceImp.UserServiceIpm;
import com.MapView.BackEnd.dtos.User.UserCreateDTO;
import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/user")

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
}
