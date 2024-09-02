package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.CostCenter.CostCenterCreateDTO;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.repository.CostCenterRepository;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.service.CostCenterService;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterDetailsDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterUpdateDTO;
import com.MapView.BackEnd.entities.CostCenter;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostCenterServiceImp implements CostCenterService {


    private  final CostCenterRepository costCenterRepository;
    private final UserLogRepository userLogRepository;
    private  final UserRepository userRepository;

    public CostCenterServiceImp(CostCenterRepository costCenterRepository, UserLogRepository userLogRepository, UserRepository userRepository) {
        this.costCenterRepository = costCenterRepository;
        this.userLogRepository = userLogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CostCenterDetailsDTO getCostCenter(Long id_cost_center,Long user_id) {
        CostCenter costCenter = this.costCenterRepository.findById(id_cost_center).orElseThrow(() -> new NotFoundException("Id not found"));
        Users user = this.userRepository.findById(user_id).orElseThrow(() -> new NotFoundException("Id not found"));

        if (!costCenter.status_check()){
            return null;
        }
        var userLog = new UserLog(user,"CostCenter",id_cost_center,"Read CostCenter", EnumAction.READ);
        userLogRepository.save(userLog);
        return new CostCenterDetailsDTO(costCenter);
    }

    @Override
    public List<CostCenterDetailsDTO> getAllCostCenter(int page,int itens) {
        Users user = this.userRepository.findById(user_id).orElseThrow(() -> new NotFoundException("Id not found"));
        return costCenterRepository.findAllByOperativeTrue(PageRequest.of(page, itens)).stream().map(CostCenterDetailsDTO::new).toList();
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
