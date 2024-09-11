package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Enviroment.EnviromentCreateDTO;
import com.MapView.BackEnd.dtos.Enviroment.EnviromentDetailsDTO;
import com.MapView.BackEnd.dtos.Enviroment.EnviromentUpdateDTO;

import java.util.List;

public interface EnviromentService {

    EnviromentDetailsDTO getEnviroment(Long enviroment_id, Long userLog_id);
    List<EnviromentDetailsDTO> getAllEnviroment(int page,int itens, Long userLog_id);
    EnviromentDetailsDTO createEnviroment(EnviromentCreateDTO data, Long userLog_id);
    EnviromentDetailsDTO updateEnviroment(Long enviroment_id,EnviromentUpdateDTO data, Long userLog_id);
    void activateEnviroment(Long id_environment, Long userLog_id);
    void inactivateEnviroment(Long id_environment, Long userLog_id);
}
 