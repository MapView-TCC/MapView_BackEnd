package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Area.AreaCreateDTO;
import com.MapView.BackEnd.dtos.Area.AreaDetailsDTO;
import com.MapView.BackEnd.dtos.Area.AreaUpdateDTO;

import java.util.List;

public interface AreaService {

    AreaDetailsDTO getArea(Long user_id,Long id_area);
    List<AreaDetailsDTO> getAllArea(int page, int itens,Long user_id);
    AreaDetailsDTO createArea(AreaCreateDTO dados,Long user_id);
    AreaDetailsDTO updateArea(Long id_area, AreaUpdateDTO dados,Long user_id);
    void activateArea(Long id_area,Long user_id); // put
    void inactivateArea(Long id_area,Long user_id); // put

}
