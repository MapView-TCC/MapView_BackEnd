package com.MapView.BackEnd.ServiceImp;

import com.MapView.BackEnd.Repository.AreaRepository;
import com.MapView.BackEnd.Service.AreaService;
import com.MapView.BackEnd.dtos.Area.AreaCreateDTO;
import com.MapView.BackEnd.dtos.Area.AreaDetailsDTO;
import com.MapView.BackEnd.dtos.Area.AreaUpdateDTO;
import com.MapView.BackEnd.entities.Area;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImp implements AreaService {


    private final AreaRepository areaRepository;

    public AreaServiceImp(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Override
    public AreaDetailsDTO getArea(Long id_area) {
        Area area = this.areaRepository.findById(id_area).orElseThrow(() -> new NotFoundException("Id not found"));

        if (!area.check_status()){
            return null;
        }

        return new AreaDetailsDTO(area);
    }

    @Override
    public List<AreaDetailsDTO> getAllArea() {
        return areaRepository.findAllByOperativeTrue().stream().map(AreaDetailsDTO::new).toList();
    }

    @Override
    public AreaDetailsDTO createArea(AreaCreateDTO dados) {
        var area = new Area(dados);
        areaRepository.save(area);
        return new AreaDetailsDTO(area);
    }

    @Override
    public AreaDetailsDTO updateArea(Long id_area, AreaUpdateDTO dados) {
        var area = areaRepository.findById(id_area).orElseThrow(() -> new NotFoundException("Id not found"));

        if (dados.area_name() != null){
            area.setArea_name(dados.area_name());
        }

        if (dados.area_code() != null){
            area.setArea_code(dados.area_code());
        }

        areaRepository.save(area);
        return new AreaDetailsDTO(area);
    }

    @Override
    public void activateArea(Long id_area) {
        var areaClass = areaRepository.findById(id_area);
        if (areaClass.isPresent()){
            var area = areaClass.get();
            area.setOperative(true);
        }
    }

    @Override
    public void inactivateArea(Long id_area) {
        var areaClass = areaRepository.findById(id_area);
        if (areaClass.isPresent()){
            var area = areaClass.get();
            area.setOperative(false);
        }
    }
}
