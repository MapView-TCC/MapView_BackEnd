package com.MapView.BackEnd.service;


import com.MapView.BackEnd.dtos.CostCenter.CostCenterCreateDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterDetailsDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterUpdateDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CostCenterService {

    CostCenterDetailsDTO getCostCenter(Long id_cost_center,Long userLog_id);
    List<CostCenterDetailsDTO> getAllCostCenter(Long userLog_id);
    CostCenterDetailsDTO createCostCenter(CostCenterCreateDTO dados,Long userLog_id);
    CostCenterDetailsDTO updateCostCenter(Long id, CostCenterUpdateDTO dados,Long userLog_id);
    void activateCostCenter(Long id_cost_cente,Long userLog_id); // put
    void inactivateCostCenter(Long id_cost_center,Long userLog_id); // put
}
