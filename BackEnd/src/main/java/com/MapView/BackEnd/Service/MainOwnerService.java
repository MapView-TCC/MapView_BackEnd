package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.dtos.MainOwner.MainOwnerDTO;
import com.MapView.BackEnd.entities.CostCenter;

public interface MainOwnerService {
    void getMainOwner(Long id_owner);
    void getAllMainOwner();
    void createMainOwner(MainOwnerDTO mainOwnerDTO);
    void updateMainOwner(String owner_name, CostCenter id_cost_center);
    void activateMainOwner(Long id_owner); // put
    void inactivateMainOwner(Long id_owner); // put
}
