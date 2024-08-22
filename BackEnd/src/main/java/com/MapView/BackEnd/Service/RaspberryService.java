package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.dtos.Raspberry.RaspberryCreateDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryDetailsDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryUpdateDTO;
import com.MapView.BackEnd.entities.Area;
import com.MapView.BackEnd.entities.Building;

import java.util.List;

public interface RaspberryService {

    RaspberryDetailsDTO getRaspberry(Long id_Raspberry);
    List<RaspberryDetailsDTO> getAllRaspberry();
    RaspberryDetailsDTO createRaspberry(RaspberryCreateDTO raspberryCreateDTO);
    RaspberryDetailsDTO updateRaspberry(Long id_raspberry, RaspberryUpdateDTO dados);
    void activeRaspberry(Long id_Raspberry);
    void inactivateRaspberry(Long id_Raspberry);
}
