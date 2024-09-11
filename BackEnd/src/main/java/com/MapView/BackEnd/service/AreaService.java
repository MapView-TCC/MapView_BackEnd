package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Area.AreaCreateDTO;
import com.MapView.BackEnd.dtos.Area.AreaDetailsDTO;
import com.MapView.BackEnd.dtos.Area.AreaUpdateDTO;

import java.util.List;

public interface AreaService {

    AreaDetailsDTO getArea(Long userLog_id,Long id_area);
    List<AreaDetailsDTO> getAllArea(int page, int itens,Long userLog_id);
    AreaDetailsDTO createArea(AreaCreateDTO dados,Long userLog_id);
    AreaDetailsDTO updateArea(Long id_area, AreaUpdateDTO dados,Long userLog_id);
    void activateArea(Long id_area,Long userLog_id); // put
    void inactivateArea(Long id_area,Long userLog_id); // put

}
