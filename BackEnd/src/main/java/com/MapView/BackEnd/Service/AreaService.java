package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.dtos.Area.AreaCreateDTO;
import com.MapView.BackEnd.dtos.Area.AreaDetailsDTO;
import com.MapView.BackEnd.dtos.Area.AreaUpdateDTO;

import java.util.List;

public interface AreaService {

    AreaDetailsDTO getArea(Long id_area);
    List<AreaDetailsDTO> getAllArea();
    AreaDetailsDTO createArea(AreaCreateDTO dados);
    AreaDetailsDTO updateArea(Long id_area, AreaUpdateDTO dados);
    void activateArea(Long id_area); // put
    void inactivateArea(Long id_area); // put

}
