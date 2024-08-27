package com.MapView.BackEnd.service;


import com.MapView.BackEnd.dtos.CostCenter.CostCenterCreateDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterDetailsDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterUpdateDTO;

import java.util.List;

public interface CostCenterService {

    CostCenterDetailsDTO getCostCenter(Long id_cost_center);
    List<CostCenterDetailsDTO> getAllCostCenter();
    CostCenterDetailsDTO createCostCenter(CostCenterCreateDTO dados);
    CostCenterDetailsDTO updateCostCenter(Long id, CostCenterUpdateDTO dados);
    void activateCostCenter(Long id_cost_center); // put
    void inactivateCostCenter(Long id_cost_center); // put
}
