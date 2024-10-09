package com.MapView.BackEnd.dtos.FormsRegisterEnviroment;

import com.MapView.BackEnd.dtos.Building.BuildingDetailsDTO;
import com.MapView.BackEnd.dtos.Environment.EnvironmentDetailsDTO;

public record FormsRegisterEnviromentDetailsDTO(Long id_build,
                                                Long id_environment) {


    public FormsRegisterEnviromentDetailsDTO(BuildingDetailsDTO dataBuilding, EnvironmentDetailsDTO dataEnvironment){
        this(dataBuilding.id_building(),dataEnvironment.id_environment());
    }
}
