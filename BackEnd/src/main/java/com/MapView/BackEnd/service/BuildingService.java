package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Building.BuildingCreateDTO;
import com.MapView.BackEnd.dtos.Building.BuildingDetailsDTO;
import com.MapView.BackEnd.dtos.Building.BuildingUpdateDTO;

import java.util.List;

public interface BuildingService {

    BuildingDetailsDTO getBuilding(Long id_building,Long userLog_id);
    List<BuildingDetailsDTO> getAllBuilding(Long userLog_id);
    BuildingDetailsDTO createBuilding(BuildingCreateDTO dados,Long userLog_id);
    BuildingDetailsDTO updateBuilding(Long id_building, BuildingUpdateDTO dados,Long userLog_id);
    void activateBuilding(Long id_building, Long userLog_id); // put
    void inactivateBuilding(Long id_building, Long userLog_id); // put

}
