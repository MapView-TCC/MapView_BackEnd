package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.User.UserCreateDTO;
import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import com.MapView.BackEnd.entities.Role;
import com.MapView.BackEnd.entities.UserRole;
import com.MapView.BackEnd.repository.RoleRespository;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.repository.UserRoleRepository;
import com.MapView.BackEnd.service.UserService;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.RoleUser;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class UserServiceIpm implements UserService {

    private final UserRepository userRepository;
    private final RoleRespository roleRespository;
    private final UserRoleRepository userRoleRepository;

    public UserServiceIpm(UserRepository userRepository, RoleRespository roleRespository, UserRoleRepository userRoleRepository) {
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

    public UserDetailsDTO loggedUser(Jwt jwt){
        String userName = jwt.getClaimAsString("email");
        user

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
    public void setPrivilege(Long user_id, RoleUser roleUser) {
        var user = userRepository.findById(user_id).orElseThrow(()-> new NotFoundException("User Id Not Found"));
        if(user.status_check()){
            user.setRole(roleUser);
            userRepository.save(user);
        }
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
