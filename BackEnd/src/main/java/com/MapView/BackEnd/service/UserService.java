package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.User.UserCreateDTO;
import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import com.MapView.BackEnd.enums.RoleUser;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserService {
    UserDetailsDTO getUser(Long id_UserService);
    List<UserDetailsDTO> getAllUser(int page, int itens);
    UserDetailsDTO createUser(UserCreateDTO data);
    void setPrivilege (Long user_id, RoleUser roleUser);
    void activeUser(Long id_UserService);
    void inactivateUser(Long id_UserService);
}
