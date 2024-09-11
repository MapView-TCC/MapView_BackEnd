package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Raspberry.RaspberryCreateDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryDetailsDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryUpdateDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RaspberryService {

    RaspberryDetailsDTO getRaspberry(String id_Raspberry, Long userLog_id);
    List<RaspberryDetailsDTO> getAllRaspberry(int page, int itens, Long userLog_id);
    RaspberryDetailsDTO createRaspberry(RaspberryCreateDTO raspberryCreateDTO, Long userLog_id);
    RaspberryDetailsDTO updateRaspberry(String id_raspberry, RaspberryUpdateDTO dados, Long userLog_id);
    void activeRaspberry(String id_Raspberry, Long userLog_id);
    void inactivateRaspberry(String id_Raspberry,Long userLog_id);
}
