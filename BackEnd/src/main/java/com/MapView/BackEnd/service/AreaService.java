package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Area.AreaCreateDTO;
import com.MapView.BackEnd.dtos.Area.AreaDetailsDTO;
import com.MapView.BackEnd.dtos.Area.AreaUpdateDTO;

import java.util.List;

public interface AreaService {

    AreaDetailsDTO getArea(Long id_area);
    List<AreaDetailsDTO> getAllArea(int page,int itens);
    AreaDetailsDTO createArea(AreaCreateDTO dados,Long user_id);
    AreaDetailsDTO updateArea(Long id_area, AreaUpdateDTO dados,Long user_id);
    void activateArea(Long id_area); // put
    void inactivateArea(Long id_area); // put

}
