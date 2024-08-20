package com.MapView.BackEnd.ServiceImp;

import com.MapView.BackEnd.Dtos.CostCenter.CostCenterDTO;
import com.MapView.BackEnd.Repository.CostCenterRepository;
import com.MapView.BackEnd.Service.CostCenterService;
import com.MapView.BackEnd.entities.CostCenter;
import org.springframework.beans.factory.annotation.Autowired;

public class CostCenterServiceImp implements CostCenterService {

    @Autowired
    private CostCenterRepository costCenterRepository;

    @Override
    public void getCostCenter(Long id_cost_center) {

    }

    @Override
    public void getAllCostCenter() {

    }

    @Override
    public void createCostCenter(CostCenterDTO dados) {
        costCenterRepository.save(new CostCenter(dados));
    }

    @Override
    public void updateCostCenter(String cost_center_name) {

    }

    @Override
    public void activateCostCenter(Long id_cost_center) {

    }

    @Override
    public void inactivateCostCenter(Long id_cost_center) {

    }
}
