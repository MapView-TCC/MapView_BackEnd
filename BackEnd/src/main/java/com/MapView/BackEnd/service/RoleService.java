package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.RoleCreateDTO;
import com.MapView.BackEnd.dtos.RoleDetailsDTO;
import com.MapView.BackEnd.entities.Role;

public interface RoleService {

    RoleDetailsDTO createRole (RoleCreateDTO data);
    RoleDetailsDTO getRole (Long role_id);



}
