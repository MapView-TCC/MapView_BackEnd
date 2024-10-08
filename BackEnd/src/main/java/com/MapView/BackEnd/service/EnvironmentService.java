package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Environment.EnvironmentCreateDTO;
import com.MapView.BackEnd.dtos.Environment.EnvironmentDetailsDTO;
import com.MapView.BackEnd.dtos.Environment.EnvironmentUpdateDTO;

import java.util.List;

public interface EnvironmentService {

    EnvironmentDetailsDTO getEnvironment(Long environment_id, Long userLog_id);
    List<EnvironmentDetailsDTO> getAllEnvironment(Long userLog_id);
    EnvironmentDetailsDTO createEnvironment(EnvironmentCreateDTO data, Long userLog_id);
    EnvironmentDetailsDTO updateEnvironment(Long environment_id, EnvironmentUpdateDTO data, Long userLog_id);
    void activateEnvironment(Long id_environment, Long userLog_id);
    void inactivateEnvironment(Long id_environment, Long userLog_id);
}
