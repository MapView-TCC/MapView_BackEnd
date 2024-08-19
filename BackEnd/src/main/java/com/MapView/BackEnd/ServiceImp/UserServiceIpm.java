package com.MapView.BackEnd.ServiceImp;

import com.MapView.BackEnd.dtos.User.UserCreateDTO;
import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import com.MapView.BackEnd.Repository.UserRepository;
import com.MapView.BackEnd.Service.UserService;
import com.MapView.BackEnd.dtos.User.UserUpdateDTO;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.RoleUser;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
@Service

public class UserServiceIpm implements UserService {

    private final UserRepository userRepository;

    public UserServiceIpm(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public List<UserDetailsDTO> getAllUser(String UserService) {
        return this.userRepository.findByOperativeTrue().stream().map(UserDetailsDTO::new).toList();
    }


    @Override
    public UserDetailsDTO createUser(String email) {
        var user = new Users(email);
        userRepository.save(user);
        return new UserDetailsDTO(user);
    }

    @Override
    public void setPrivilege(Users users, RoleUser roleUser) {
        users.setRole(roleUser);
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
