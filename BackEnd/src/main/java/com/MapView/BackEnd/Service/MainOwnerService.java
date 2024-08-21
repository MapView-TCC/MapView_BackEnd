package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.dtos.MainOwner.MainOwnerCreateDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerDetailsDTO;
import com.MapView.BackEnd.entities.CostCenter;

import java.util.List;

public interface MainOwnerService {
    MainOwnerDetailsDTO getMainOwner(String id_owner);
    List<MainOwnerDetailsDTO> getAllMainOwner();
    MainOwnerDetailsDTO createMainOwner(MainOwnerCreateDTO mainOwnerCreateDTO);
    void updateMainOwner(String owner_name, CostCenter id_cost_center);
    void activateMainOwner(String id_owner); // put
    void inactivateMainOwner(String id_owner); // put
}
