package com.MapView.BackEnd.service;


import com.MapView.BackEnd.dtos.CostCenter.CostCenterCreateDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterDetailsDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterUpdateDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CostCenterService {

    CostCenterDetailsDTO getCostCenter(Long id_cost_center,Long user_id);
    List<CostCenterDetailsDTO> getAllCostCenter(int page, int itens,Long user_id);
    CostCenterDetailsDTO createCostCenter(CostCenterCreateDTO dados,Long user_id);
    CostCenterDetailsDTO updateCostCenter(Long id, CostCenterUpdateDTO dados,Long user_id);
    void activateCostCenter(Long id_cost_cente,Long user_id); // put
    void inactivateCostCenter(Long id_cost_center,Long user_id); // put
}
