package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Building.BuildingCreateDTO;
import com.MapView.BackEnd.dtos.Building.BuildingDetailsDTO;
import com.MapView.BackEnd.dtos.Building.BuildingUpdateDTO;
import com.MapView.BackEnd.entities.Building;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.infra.Exception.*;
import com.MapView.BackEnd.repository.BuildingRepository;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.service.BuildingService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImp implements BuildingService {


    private final BuildingRepository buildingRepository;
    private final UserRepository userRepository;
    private  final UserLogRepository userLogRepository;

    public BuildingServiceImp(BuildingRepository buildingRepository, UserRepository userRepository, UserLogRepository userLogRepository) {
        this.buildingRepository = buildingRepository;
        this.userRepository = userRepository;
        this.userLogRepository = userLogRepository;
    }

    @Override
    public BuildingDetailsDTO getBuilding(Long building_id, Long userLog_id) {
        Building building = this.buildingRepository.findById(building_id)
                .orElseThrow(() -> new NotFoundException("Building with ID " + building_id + " not found"));

        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        if (!building.isOperative()) {
            throw new OperativeFalseException("Cannot read an inactive building with ID " + building_id);
        }

        var userLog = new UserLog(user, "Building", building_id.toString(), "Read building", EnumAction.READ);
        userLogRepository.save(userLog);

        return new BuildingDetailsDTO(building);
    }

    @Override
    public List<BuildingDetailsDTO> getAllBuilding(Long userLog_id) {
        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        Building building = new Building();
        var userLog = new UserLog(user, "Building", building.getBuilding_code(), "Read all building", EnumAction.READ);
        userLogRepository.save(userLog);

        return buildingRepository.findAllByOperativeTrue().stream()
                .map(BuildingDetailsDTO::new)
                .toList();
    }

    @Override
    public BuildingDetailsDTO createBuilding(BuildingCreateDTO dados,Long userLog_id) {
        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        try {
            // Cria o building a partir do DTO
            var building = new Building(dados);

            // Salva o building e obtém o objeto salvo com o ID gerado
            Building savedBuilding = buildingRepository.save(building);

            // Cria um log do usuário
            var userLog = new UserLog(user, "Building", savedBuilding.getId_building().toString(), "Create new Building", EnumAction.CREATE);
            userLogRepository.save(userLog);

            // Retorna o DTO usando o objeto salvo, que contém o ID correto
            return new BuildingDetailsDTO(savedBuilding);

        } catch (DataIntegrityViolationException e) {
            throw new ExistingEntityException("Building with code " + dados.building_code() + " already exists.");
        }
    }

    @Override
    public BuildingDetailsDTO updateBuilding(Long building_id, BuildingUpdateDTO data, Long userLog_id) {
        Building building = buildingRepository.findById(building_id)
                .orElseThrow(() -> new NotFoundException("Building with ID " + building_id + " not found"));

        if (!building.isOperative()) {
            throw new OperativeFalseException("Cannot update an inactive building with ID " + building_id);
        }

        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        var userlog = new UserLog(user, "Building", building_id.toString(), null, "Update building: ", EnumAction.UPDATE);

        if (data.building_code() != null) {
            if (data.building_code().isBlank()) {
                throw new BlankErrorException("Building code cannot be blank.");
            }

            building.setBuilding_code(data.building_code());

            userlog.setField("building_code");
            userlog.setDescription("Updated building_code to: " + data.building_code());
        }

        buildingRepository.save(building);
        userLogRepository.save(userlog);

        return new BuildingDetailsDTO(building);
    }

    @Override
    public void activateBuilding(Long building_id, Long userLog_id) {
        var building = buildingRepository.findById(building_id).orElseThrow(() -> new RuntimeException("Building with ID " + building_id + " not found"));

        if (building.isOperative()){
            throw new OperativeTrueException("It is already activate");

        }
        building.setOperative(true);

        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        var userLog = new UserLog(user,"Building",building_id.toString(),"Operative","Activated Area", EnumAction.UPDATE);
        buildingRepository.save(building);
        userLogRepository.save(userLog);

    }

    @Override
    public void inactivateBuilding(Long building_id,Long userLog_id) {
        var building = buildingRepository.findById(building_id).orElseThrow(() -> new RuntimeException("Building with ID " + building_id + " not found"));

        if (!building.isOperative()){
            throw new OperativeFalseException("It is already inactivate");
        }
        building.setOperative(false);

        Users user = this.userRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found"));

        var userLog = new UserLog(user,"Building",building_id.toString(),"Operative","Inactivated Area", EnumAction.UPDATE);
        buildingRepository.save(building);
        userLogRepository.save(userLog);

    }
}
