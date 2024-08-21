package com.MapView.BackEnd.Service;


import com.MapView.BackEnd.dtos.CostCenter.CostCenterCreateDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterDetailsDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterUpdateDTO;
import com.MapView.BackEnd.entities.CostCenter;

import java.util.List;
import java.util.Optional;

public interface CostCenterService {

    CostCenterDetailsDTO getCostCenter(Long id_cost_center);
    List<CostCenterDetailsDTO> getAllCostCenter();
    CostCenterDetailsDTO createCostCenter(CostCenterCreateDTO dados);
    CostCenterDetailsDTO updateCostCenter(Long id, CostCenterUpdateDTO dados);
    void activateCostCenter(Long id_cost_center); // put
    void inactivateCostCenter(Long id_cost_center); // put
}
