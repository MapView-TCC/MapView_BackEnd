package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Raspberry.RaspberryCreateDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryDetailsDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryUpdateDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RaspberryService {

    RaspberryDetailsDTO getRaspberry(Long id_Raspberry);
    List<RaspberryDetailsDTO> getAllRaspberry(int page, int itens);
    RaspberryDetailsDTO createRaspberry(RaspberryCreateDTO raspberryCreateDTO);
    RaspberryDetailsDTO updateRaspberry(Long id_raspberry, RaspberryUpdateDTO dados);
    void activeRaspberry(Long id_Raspberry);
    void inactivateRaspberry(Long id_Raspberry);
}