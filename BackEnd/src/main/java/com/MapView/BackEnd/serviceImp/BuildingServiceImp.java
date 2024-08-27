package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.repository.BuildingRepository;
import com.MapView.BackEnd.service.BuildingService;
import com.MapView.BackEnd.dtos.Building.BuildingCreateDTO;
import com.MapView.BackEnd.dtos.Building.BuildingDetailsDTO;
import com.MapView.BackEnd.dtos.Building.BuildingUpdateDTO;
import com.MapView.BackEnd.entities.Building;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImp implements BuildingService {


    private final BuildingRepository buildingRepository;

    public BuildingServiceImp(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Override
    public BuildingDetailsDTO getBuilding(Long id_building) {
        Building building = this.buildingRepository.findById(id_building).orElseThrow(() -> new NotFoundException("Id not found"));

        if (!building.status_check()){
            return null;
        }

        return new BuildingDetailsDTO(building);
    }

    @Override
    public List<BuildingDetailsDTO> getAllBuilding() {
        return buildingRepository.findAllByOperativeTrue().stream().map(BuildingDetailsDTO::new).toList();
    }

    @Override
    public BuildingDetailsDTO createBuilding(BuildingCreateDTO dados) {
        var building = new Building(dados);
        buildingRepository.save(building);
        return new BuildingDetailsDTO(building);
    }

    @Override
    public BuildingDetailsDTO updateBuilding(Long id_building, BuildingUpdateDTO dados) {
        var building = buildingRepository.findById(id_building). orElseThrow(() -> new RuntimeException("Building Id not found"));

        if (dados.building_code() != null){
            building.setBuilding_code(dados.building_code());
        }

        buildingRepository.save(building);
        return new BuildingDetailsDTO(building);
    }

    @Override
    public void activateBuilding(Long id_building) {
        var buildingClass = buildingRepository.findById(id_building);
        if (buildingClass.isPresent()){
            var building = buildingClass.get();
            building.setOperative(true);
        }
    }

    @Override
    public void inactivateBuilding(Long id_building) {
        var buildingClass = buildingRepository.findById(id_building);
        if (buildingClass.isPresent()){
            var building = buildingClass.get();
            building.setOperative(false);
        }
    }
}
