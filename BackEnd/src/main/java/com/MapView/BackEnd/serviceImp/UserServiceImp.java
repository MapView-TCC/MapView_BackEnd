package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Token;
import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import com.MapView.BackEnd.entities.Role;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.service.UserService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final RoleServiceImp roleServiceImp;


    public UserServiceImp(UserRepository userRepository, RoleServiceImp roleServiceImp) {
        this.userRepository = userRepository;


        this.roleServiceImp = roleServiceImp;
    }

    @Override
    public UserDetailsDTO getUser(Long id_user) {
        Users user = this.userRepository.findById(id_user).orElseThrow(() -> new NotFoundException("User Id not found"));
        if(!user.status_check()){
            return null;

        }
        return new UserDetailsDTO(user);
    }

    @Override
    public List<UserDetailsDTO> getAllUser() {
        return this.userRepository.findByOperativeTrue().stream().map(UserDetailsDTO::new).toList();
    }
    
    public Users loggedUser(Jwt jwt){
        String email =jwt.getClaimAsString("email");
        Users user = userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User Not Found"));
        return user;
    }

    public Token getCredencials(Jwt jwt){
        String id_token = jwt.getClaimAsString("id_token");

        return new Token(id_token);
    }

    public Users userWithoutRole(Users users){
        Role role = roleServiceImp.getRolebyName("ROLE_APRENDIZ");
        users.setRole(role);
        userRepository.save(users);
        return users;

    }

    @Override
    public void activeUser(Long id_user) {
        var userClass = this.userRepository.findById(id_user);
        if (userClass.isPresent()){
            var user = userClass.get();
            user.setOperative(true);
        }
    }
    @Override
    public void inactivateUser(Long id_user) {
        var userClass = this.userRepository.findById(id_user);
        if (userClass.isPresent()){
            var user = userClass.get();
            user.setOperative(false);
        }
    }
}
