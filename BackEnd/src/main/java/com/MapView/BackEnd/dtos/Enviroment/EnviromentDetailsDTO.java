package com.MapView.BackEnd.dtos.Enviroment;

import com.MapView.BackEnd.entities.Enviroment;
import com.MapView.BackEnd.entities.Raspberry;

public record EnviromentDetailsDTO(Long id_enviroment,String environment_name, Raspberry raspberry) {
    public EnviromentDetailsDTO(Enviroment enviroment){
        this(enviroment.getId_environment(),enviroment.getEnvironment_name(),enviroment.getRaspberry());
    }
}
