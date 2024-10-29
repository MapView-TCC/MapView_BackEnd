package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.MainOwner.MainOwnerCreateDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerDetailsDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerUpdateDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MainOwnerService {
    MainOwnerDetailsDTO getMainOwner(Long id_owner,Long userLog_id);
    List<MainOwnerDetailsDTO> getAllMainOwner(Long userLog_id);
    MainOwnerDetailsDTO createMainOwner(MainOwnerCreateDTO mainOwnerCreateDTO,Long userLog_id);
    MainOwnerDetailsDTO updateMainOwner(Long id_owner, MainOwnerUpdateDTO id_cost_center,Long userLog_id);
    void activateMainOwner(Long id_owner,Long userLog_id); // put
    void inactivateMainOwner(Long id_owner,Long userLog_id); // put
}
