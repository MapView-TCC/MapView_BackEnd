package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.Dtos.MainOwner.MainOwnerDTO;
import com.MapView.BackEnd.entities.CostCenter;
import com.MapView.BackEnd.entities.Enviroment;
import com.MapView.BackEnd.entities.Post;

public interface MainOwnerService {
    void getMainOwner(Long id_owner);
    void getAllMainOwner();
    void createMainOwner(MainOwnerDTO mainOwnerDTO);
    void updateMainOwner(String owner_name, CostCenter id_cost_center);
    void activateMainOwner(Long id_owner); // put
    void inactivateMainOwner(Long id_owner); // put
}
