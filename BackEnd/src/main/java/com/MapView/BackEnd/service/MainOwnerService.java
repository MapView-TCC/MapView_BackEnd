package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.MainOwner.MainOwnerCreateDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerDetailsDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerUpdateDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MainOwnerService {
    MainOwnerDetailsDTO getMainOwner(String id_owner);
    List<MainOwnerDetailsDTO> getAllMainOwner(int page, int itens);
    MainOwnerDetailsDTO createMainOwner(MainOwnerCreateDTO mainOwnerCreateDTO);
    MainOwnerDetailsDTO updateMainOwner(String owner_name, MainOwnerUpdateDTO id_cost_center);
    void activateMainOwner(String id_owner); // put
    void inactivateMainOwner(String id_owner); // put
}
