package com.MapView.BackEnd.Service;


import com.MapView.BackEnd.dtos.CostCenter.CostCenterDTO;

public interface CostCenterService {

    void getCostCenter(Long id_cost_center);
    void getAllCostCenter();
    void createCostCenter(CostCenterDTO dados);
    void updateCostCenter(String cost_center_name);
    void activateCostCenter(Long id_cost_center); // put
    void inactivateCostCenter(Long id_cost_center); // put
}
