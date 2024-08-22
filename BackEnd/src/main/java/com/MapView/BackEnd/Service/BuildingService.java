package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.dtos.Building.BuildingCreateDTO;
import com.MapView.BackEnd.dtos.Building.BuildingDetailsDTO;
import com.MapView.BackEnd.dtos.Building.BuildingUpdateDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterCreateDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterDetailsDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterUpdateDTO;

import java.util.List;

public interface BuildingService {

    BuildingDetailsDTO getBuilding(Long id_building);
    List<BuildingDetailsDTO> getAllBuilding();
    BuildingDetailsDTO createBuilding(BuildingCreateDTO dados);
    BuildingDetailsDTO updateBuilding(Long id_building, BuildingUpdateDTO dados);
    void activateBuilding(Long id_building); // put
    void inactivateBuilding(Long id_building); // put

}
