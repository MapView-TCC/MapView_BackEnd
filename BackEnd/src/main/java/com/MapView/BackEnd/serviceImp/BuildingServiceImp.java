package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.repository.BuildingRepository;
import com.MapView.BackEnd.service.BuildingService;
import com.MapView.BackEnd.dtos.Building.BuildingCreateDTO;
import com.MapView.BackEnd.dtos.Building.BuildingDetailsDTO;
import com.MapView.BackEnd.dtos.Building.BuildingUpdateDTO;
import com.MapView.BackEnd.entities.Building;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImp implements BuildingService {


    private final BuildingRepository buildingRepository;
    private final UserLogImp userLogImp;

    public BuildingServiceImp(BuildingRepository buildingRepository, UserLogImp userLogImp) {
        this.buildingRepository = buildingRepository;
        this.userLogImp = userLogImp;
    }

    @Override
    public BuildingDetailsDTO getBuilding(Long id_building,Long user_id) {
        Building building = this.buildingRepository.findById(id_building).orElseThrow(() -> new NotFoundException("Id not found"));
        if (!building.status_check()){
            return null;
        }
        var userLog = new UserLog(null,"Building",id_building,"Read building",EnumAction.READ);
        userLogImp.createUserLog(user_id,userLog);

        return new BuildingDetailsDTO(building);
    }

    @Override
    public List<BuildingDetailsDTO> getAllBuilding( int page,  int itens) {
        return buildingRepository.findAllByOperativeTrue(PageRequest.of(page, itens)).stream().map(BuildingDetailsDTO::new).toList();
    }

    @Override
    public BuildingDetailsDTO createBuilding(BuildingCreateDTO dados,Long user_id) {
        var building = new Building(dados);
        Long id_build = buildingRepository.save(building).getId_building();

        var userLog = new UserLog(null,"Building",id_build,"Create new Building", EnumAction.CREATE);
        userLogImp.createUserLog(user_id,userLog);
        return new BuildingDetailsDTO(building);
    }

    @Override
    public BuildingDetailsDTO updateBuilding(Long id_building, BuildingUpdateDTO dados,Long user_id) {
        var building = buildingRepository.findById(id_building). orElseThrow(() -> new RuntimeException("Building Id not found"));
        var userlog = new UserLog(null,"Building",id_building,null,"Update building: ",EnumAction.UPDATE);

        if (dados.building_code() != null){
            building.setBuilding_code(dados.building_code());

            userlog.setField("building_code");
            userlog.setDescription("building_code to:"+ dados.building_code());
        }

        buildingRepository.save(building);
        userLogImp.createUserLog(user_id,userlog);
        return new BuildingDetailsDTO(building);
    }

    @Override
    public void activateBuilding(Long id_building, Long user_id) {
        var buildingClass = buildingRepository.findById(id_building);
        if (buildingClass.isPresent()){
            var building = buildingClass.get();
            building.setOperative(true);
        }
        var userLog = new UserLog(null,"Building",id_building,"Operative","Activated Area", EnumAction.UPDATE);
        userLogImp.createUserLog(user_id,userLog);
    }

    @Override
    public void inactivateBuilding(Long id_building,Long user_id) {
        var buildingClass = buildingRepository.findById(id_building);
        if (buildingClass.isPresent()){
            var building = buildingClass.get();
            building.setOperative(false);
        }
        var userLog = new UserLog(null,"Building",id_building,"Operative","Inactivated Area", EnumAction.UPDATE);
         userLogImp.createUserLog(user_id,userLog);
    }
}
