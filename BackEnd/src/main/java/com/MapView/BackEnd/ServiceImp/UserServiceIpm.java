package com.MapView.BackEnd.ServiceImp;

import com.MapView.BackEnd.Dtos.User.UserCreateDTO;
import com.MapView.BackEnd.Dtos.User.UserDetailsDto;
import com.MapView.BackEnd.Repository.UserRepository;
import com.MapView.BackEnd.Service.UserService;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.RoleUser;

import java.util.List;

public class UserServiceIpm implements UserService {

    private final UserRepository userRepository;

    public UserServiceIpm(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void getUserService(Long id_UserService) {

    }

    @Override
    public List<UserDetailsDto> getAllUser(String UserService) {
        return this.userRepository.findByOperativeTrue().stream().map(UserDetailsDto::new).toList();
    }


    @Override
    public void createUser(UserCreateDTO data) {
        var user = new Users(data);
        userRepository.save(data);
    }

    @Override
    public void setPrivilege(Users users, RoleUser roleUser) {
        users.setRole(roleUser);
    }


    @Override
    public void updateUserService(String UserService) {

    }

    @Override
    public void activeUserService(Long id_UserService) {

    }

    @Override
    public void inactivateEnviroment(Long id_UserService) {

    }
}
