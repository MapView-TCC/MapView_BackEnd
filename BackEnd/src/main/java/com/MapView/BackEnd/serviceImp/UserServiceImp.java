package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Token;
import com.MapView.BackEnd.dtos.TokenDetailsDTO;
import com.MapView.BackEnd.dtos.User.UserCreateDTO;
import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import com.MapView.BackEnd.dtos.UserRole.UserRoleDetailsDTO;
import com.MapView.BackEnd.entities.Role;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.UserRole;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import com.MapView.BackEnd.repository.RoleRespository;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.repository.UserRoleRepository;
import com.MapView.BackEnd.service.UserRoleService;
import com.MapView.BackEnd.service.UserService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service

public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final RoleRespository roleRespository;
    private final UserRoleRepository userRoleRepository;


    public UserServiceImp(UserRepository userRepository, RoleRespository roleRespository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.roleRespository = roleRespository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetailsDTO getUser(Long id_user) {
        Users user = this.userRepository.findById(id_user)
                .orElseThrow(() -> new NotFoundException("User with ID " + id_user + " not found"));
        if (!user.status_check()) {
            return null;

        }
        return new UserDetailsDTO(user);
    }

    @Override
    public List<UserDetailsDTO> getAllUser() {
        return this.userRepository.findByOperativeTrue().stream().map(UserDetailsDTO::new).toList();
    }

    public UserRoleDetailsDTO loggedUserRole(Jwt jwt){
        String email = jwt.getClaimAsString("email");
        
        Users user  = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        UserRole userRole = userRoleRepository.findByUser(user).orElseThrow(() ->new NotFoundException("Not found role "));

        return new UserRoleDetailsDTO(userRole);

    }
    
    public Map<String, String> loggedUser(Jwt jwt){
        Map<String, String> attributesMap = new HashMap<>();
        String id_token = jwt.getClaimAsString("id_token");
        String email =jwt.getClaimAsString("email");

        attributesMap.put("id_token", id_token);
        attributesMap.put("email", email);


        return attributesMap;
    }
    public Token getCredencials(Jwt jwt){
        String id_token = jwt.getClaimAsString("id_token");

        return new Token(id_token);
    }



    @Override
    public UserDetailsDTO createUser(UserCreateDTO data) {
        Users user = new Users(data.email());
        Role role = roleRespository.findById(data.role_id()).orElseThrow(()-> new NotFoundException("Role not found"));
        UserRole userRole = userRoleRepository.save(new UserRole(user,role));
        userRepository.save(user);

        return new UserDetailsDTO(user);
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
