package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Building.BuildingCreateDTO;
import com.MapView.BackEnd.dtos.Building.BuildingDetailsDTO;
import com.MapView.BackEnd.dtos.Building.BuildingUpdateDTO;

import java.util.List;

public interface BuildingService {

    BuildingDetailsDTO getBuilding(Long id_building,Long user_id);
    List<BuildingDetailsDTO> getAllBuilding(int page, int itens);
    BuildingDetailsDTO createBuilding(BuildingCreateDTO dados,Long user_id);
    BuildingDetailsDTO updateBuilding(Long id_building, BuildingUpdateDTO dados,Long user_id);
    void activateBuilding(Long id_building, Long user_id); // put
    void inactivateBuilding(Long id_building, Long user_id); // put

}
