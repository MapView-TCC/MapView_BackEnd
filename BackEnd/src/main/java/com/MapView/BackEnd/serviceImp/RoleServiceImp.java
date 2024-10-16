package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.RoleCreateDTO;
import com.MapView.BackEnd.dtos.RoleDetailsDTO;
import com.MapView.BackEnd.entities.Role;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import com.MapView.BackEnd.repository.RoleRespository;
import com.MapView.BackEnd.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp implements RoleService {
    private final RoleRespository roleRespository;

    public RoleServiceImp(RoleRespository roleRespository) {
        this.roleRespository = roleRespository;
    }

    @Override
    public RoleDetailsDTO createRole(RoleCreateDTO data) {
        Role entityRole = new Role(data);
        roleRespository.save(entityRole);
        return new RoleDetailsDTO(entityRole);
    }

    @Override
    public RoleDetailsDTO getRole(Long role_id) {
        return new RoleDetailsDTO(this.roleRespository.findById(role_id).orElseThrow(() -> new NotFoundException("Role note found")));
    }
}
