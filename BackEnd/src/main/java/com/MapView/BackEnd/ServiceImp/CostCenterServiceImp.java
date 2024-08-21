package com.MapView.BackEnd.ServiceImp;

import com.MapView.BackEnd.dtos.CostCenter.CostCenterCreateDTO;
import com.MapView.BackEnd.Repository.CostCenterRepository;
import com.MapView.BackEnd.Service.CostCenterService;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterDetailsDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterUpdateDTO;
import com.MapView.BackEnd.entities.CostCenter;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CostCenterServiceImp implements CostCenterService {

    @Autowired
    private CostCenterRepository costCenterRepository;

    @Override
    public CostCenterDetailsDTO getCostCenter(Long id_cost_center) {
        CostCenter costCenter = this.costCenterRepository.findById(id_cost_center).orElseThrow(() -> new NotFoundException("Id not found"));

        if (!costCenter.status_check()){
            return null;
        }
        return new CostCenterDetailsDTO(costCenter);
    }

    @Override
    public List<CostCenterDetailsDTO> getAllCostCenter() {
        return costCenterRepository.findAllByOperativeTrue().stream().map(CostCenterDetailsDTO::new).toList();
    }

    @Override
    public CostCenterDetailsDTO createCostCenter(CostCenterCreateDTO dados) {
        var costcenter = new CostCenter(dados);
        costCenterRepository.save(costcenter);
        return new CostCenterDetailsDTO(costcenter);
    }

    @Override
    public CostCenterDetailsDTO updateCostCenter(Long id, CostCenterUpdateDTO dados) {
        var costCenter =  costCenterRepository.findById(id).orElseThrow(() -> new NotFoundException("Cost Center Id Not Found"));

        if (dados.cost_center_name() != null){
            costCenter.setCost_center_name(dados.cost_center_name());
        }

        costCenterRepository.save(costCenter);
        return new CostCenterDetailsDTO(costCenter);
    }

    // funções para ativer e inativar
    @Override
    public void activateCostCenter(Long id_cost_center) {
        var costCenterClass = costCenterRepository.findById(id_cost_center);
        if (costCenterClass.isPresent()){
            var costcenter = costCenterClass.get();
            costcenter.setOperative(true);
        }
    }

    @Override
    public void inactivateCostCenter(Long id_cost_center) {
        var costCenterClass = costCenterRepository.findById(id_cost_center);
        if (costCenterClass.isPresent()){
            var costcenter = costCenterClass.get();
            costcenter.setOperative(false);
        }
    }
}
