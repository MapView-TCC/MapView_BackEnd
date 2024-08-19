package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.Dtos.User.UserCreateDTO;
import com.MapView.BackEnd.Dtos.User.UserDetailsDto;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.RoleUser;

import java.util.List;

public interface UserService {
    void getUserService(Long id_UserService);
    List<UserDetailsDto> getAllUser(String UserService);
    void createUser(UserCreateDTO data);
    void setPrivilege (Users users, RoleUser roleUser);
    void updateUserService(String UserService);
    void activeUserService(Long id_UserService);
    void inactivateEnviroment(Long id_UserService);
}
