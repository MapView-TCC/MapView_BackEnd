package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.dtos.User.UserCreateDTO;
import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import com.MapView.BackEnd.dtos.User.UserUpdateDTO;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.RoleUser;

import java.util.List;

public interface UserService {
    UserDetailsDTO getUser(Long id_UserService);
    List<UserDetailsDTO> getAllUser(String UserService);
    UserDetailsDTO createUser(String email);
    void setPrivilege (Users users, RoleUser roleUser);
    void activeUser(Long id_UserService);
    void inactivateUser(Long id_UserService);
}
