package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.repository.AreaRepository;
import com.MapView.BackEnd.repository.BuildingRepository;
import com.MapView.BackEnd.repository.RaspberryRepository;
import com.MapView.BackEnd.service.RaspberryService;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryCreateDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryDetailsDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryUpdateDTO;
import com.MapView.BackEnd.entities.Area;
import com.MapView.BackEnd.entities.Building;
import com.MapView.BackEnd.entities.Raspberry;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaspberryServiceImp implements RaspberryService {


    private final RaspberryRepository raspberryRepository;
    private final AreaRepository areaRepository;
    private final BuildingRepository buildingRepository;

    public RaspberryServiceImp(RaspberryRepository raspberryRepository, AreaRepository areaRepository, BuildingRepository buildingRepository) {
        this.raspberryRepository = raspberryRepository;
        this.areaRepository = areaRepository;
        this.buildingRepository = buildingRepository;
    }


    @Override
    public RaspberryDetailsDTO getRaspberry(Long id_Raspberry) {
        Raspberry raspberry = this.raspberryRepository.findById(id_Raspberry)
                .orElseThrow(() -> new NotFoundException("Raspberry id not found"));

        if (!raspberry.status_check()){
            throw new NotFoundException("Raspberry status is not valid");
        }

        return new RaspberryDetailsDTO(raspberry);
    }

    @Override
    public List<RaspberryDetailsDTO> getAllRaspberry() {
        return raspberryRepository.findAllByOperativeTrue().stream().map(RaspberryDetailsDTO::new).toList();
    }

    @Override
    public RaspberryDetailsDTO createRaspberry(RaspberryCreateDTO raspberryCreateDTO) {
        Building building = buildingRepository.findById(raspberryCreateDTO.id_building())
                .orElseThrow(() -> new NotFoundException("Building id not found."));

        Area area = areaRepository.findById(raspberryCreateDTO.id_area())
                .orElseThrow(() -> new NotFoundException("Area id not found"));

        Raspberry raspberry = new Raspberry(raspberryCreateDTO, building, area);

        raspberryRepository.save(raspberry);

        return new RaspberryDetailsDTO(raspberry);

    }

    @Override
    public RaspberryDetailsDTO updateRaspberry(Long id_raspberry, RaspberryUpdateDTO dados) {
        var raspberry = raspberryRepository.findById(id_raspberry)
                .orElseThrow(() -> new NotFoundException("Raspberry id not found"));

        if (dados.raspberry_name() != null){
            raspberry.setRaspberry_name(dados.raspberry_name());
        }

        if (dados.id_building() != null){
            var building = buildingRepository.findById(dados.id_building()).orElseThrow(() -> new NotFoundException("Building id not found"));
            raspberry.setId_building(building);
        }

        if (dados.id_area() != null){
            var area = areaRepository.findById(dados.id_area()).orElseThrow(() -> new NotFoundException("Area id not found"));
            raspberry.setId_area(area);
        }

        raspberryRepository.save(raspberry);
        return new RaspberryDetailsDTO(raspberry);

    }

    @Override
    public void activeRaspberry(Long id_Raspberry) {
        var raspberryClass = this.raspberryRepository.findById(id_Raspberry);
        if (raspberryClass.isPresent()){
            var raspberry = raspberryClass.get();
            raspberry.setOperative(true);
            raspberryRepository.save(raspberry);
        }
    }

    @Override
    public void inactivateRaspberry(Long id_Raspberry) {
        var raspberry = this.raspberryRepository.findById(id_Raspberry)
                .orElseThrow(() -> new NotFoundException("Raspberry id not found."));
        raspberry.setOperative(false);
        raspberryRepository.save(raspberry);



    }
}
