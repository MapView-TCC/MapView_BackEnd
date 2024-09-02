package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.MainOwner.MainOwnerCreateDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerDetailsDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerUpdateDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MainOwnerService {
    MainOwnerDetailsDTO getMainOwner(String id_owner,Long user_id);
    List<MainOwnerDetailsDTO> getAllMainOwner(int page, int itens,Long user_id);
    MainOwnerDetailsDTO createMainOwner(MainOwnerCreateDTO mainOwnerCreateDTO,Long user_id);
    MainOwnerDetailsDTO updateMainOwner(String owner_name, MainOwnerUpdateDTO id_cost_center,Long user_id);
    void activateMainOwner(String id_owner,Long user_id); // put
    void inactivateMainOwner(String id_owner,Long user_id); // put
}
