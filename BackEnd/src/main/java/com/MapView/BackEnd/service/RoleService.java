package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Role.RoleCreateDTO;
import com.MapView.BackEnd.dtos.Role.RoleDetailsDTO;
import com.MapView.BackEnd.entities.Role;

public interface RoleService {

    RoleDetailsDTO createRole (RoleCreateDTO data);
    RoleDetailsDTO getRole (Long role_id);
    Role getRolebyName(String name);



}
