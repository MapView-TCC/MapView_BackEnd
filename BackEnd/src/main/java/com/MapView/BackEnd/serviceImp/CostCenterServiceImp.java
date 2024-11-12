package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.CostCenter.CostCenterCreateDTO;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.infra.Exception.ExistingEntityException;
import com.MapView.BackEnd.infra.Exception.OperativeFalseException;
import com.MapView.BackEnd.infra.Exception.OperativeTrueException;
import com.MapView.BackEnd.repository.CostCenterRepository;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.service.CostCenterService;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterDetailsDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterUpdateDTO;
import com.MapView.BackEnd.entities.CostCenter;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostCenterServiceImp implements CostCenterService {

    private final CostCenterRepository costCenterRepository;
    private final UserLogRepository userLogRepository;
    private final UserRepository userRepository;

    public CostCenterServiceImp(CostCenterRepository costCenterRepository, UserLogRepository userLogRepository, UserRepository userRepository) {
        this.costCenterRepository = costCenterRepository;
        this.userLogRepository = userLogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CostCenterDetailsDTO getCostCenter(Long id_cost_center, Long userLog_id) {
        CostCenter costCenter = this.costCenterRepository.findById(id_cost_center)
                .orElseThrow(() -> new NotFoundException("CostCenter with ID " + id_cost_center + " not found"));

        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        if (!costCenter.status_check()) {
            throw new OperativeFalseException("The inactive CostCenter with ID " + id_cost_center + " cannot be read.");
        }

        var userLog = new UserLog(user, "CostCenter", id_cost_center.toString(), "Read CostCenter", EnumAction.READ);
        userLogRepository.save(userLog);

        return new CostCenterDetailsDTO(costCenter);
    }

    @Override
    public List<CostCenterDetailsDTO> getAllCostCenter(Long userLog_id) {
        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        var userLog = new UserLog(user, "CostCenter", "Read All CostCenters", EnumAction.READ);
        userLogRepository.save(userLog);

        return costCenterRepository.findAllByOperativeTrue().stream()
                .map(CostCenterDetailsDTO::new)
                .toList();
    }

    @Override
    public CostCenterDetailsDTO createCostCenter(CostCenterCreateDTO dados, Long userLog_id) {
        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        CostCenter verifyCostCenter = costCenterRepository.findByCostCenter(dados.costCenter()).orElse(null);
        if (verifyCostCenter == null) {
            try {
                var costcenter = new CostCenter(dados);
                Long cost_id = costCenterRepository.save(costcenter).getId_cost_center();

                var userLog = new UserLog(user, "CostCenter", cost_id.toString(), "Created new CostCenter", EnumAction.CREATE);
                userLogRepository.save(userLog);

                return new CostCenterDetailsDTO(costcenter);

            } catch (DataIntegrityViolationException e) {
                throw new ExistingEntityException("CostCenter with name '" + dados.costCenter() + "' already exists.");
            }
        }
        return new CostCenterDetailsDTO(verifyCostCenter);
    }

    @Override
    public CostCenterDetailsDTO updateCostCenter(Long id, CostCenterUpdateDTO dados, Long userLog_id) {
        var costCenter = costCenterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CostCenter with ID " + id + " not found"));

        if (!costCenter.isOperative()) {
            throw new OperativeFalseException("The inactive CostCenter with ID " + id + " cannot be updated.");
        }

        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        var userlog = new UserLog(user, "CostCenter", id.toString(), null, "Updated CostCenter information", EnumAction.UPDATE);

        if (dados.costCenter() != null) {
            costCenter.setCostCenter(dados.costCenter());
            userlog.setField("Updated costCenter name to: " + dados.costCenter());
        }

        costCenterRepository.save(costCenter);
        userLogRepository.save(userlog);

        return new CostCenterDetailsDTO(costCenter);
    }

    @Override
    public void activateCostCenter(Long id_cost_center, Long userLog_id) {
        var costCenter = costCenterRepository.findById(id_cost_center)
                .orElseThrow(() -> new NotFoundException("CostCenter with ID " + id_cost_center + " not found"));

        if (costCenter.isOperative()) {
            throw new OperativeTrueException("CostCenter with ID " + id_cost_center + " is already active.");
        }

        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        costCenter.setOperative(true);
        var userLog = new UserLog(user, "CostCenter", id_cost_center.toString(), "Operative", "Activated CostCenter", EnumAction.UPDATE);
        costCenterRepository.save(costCenter);
        userLogRepository.save(userLog);
    }

    @Override
    public void inactivateCostCenter(Long id_cost_center, Long userLog_id) {
        var costCenter = costCenterRepository.findById(id_cost_center)
                .orElseThrow(() -> new NotFoundException("CostCenter with ID " + id_cost_center + " not found"));

        if (!costCenter.isOperative()) {
            throw new OperativeFalseException("CostCenter with ID " + id_cost_center + " is already inactive.");
        }

        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        costCenter.setOperative(false);
        var userLog = new UserLog(user, "CostCenter", id_cost_center.toString(), "Operative", "Inactivated CostCenter", EnumAction.UPDATE);
        costCenterRepository.save(costCenter);
        userLogRepository.save(userLog);
    }
}