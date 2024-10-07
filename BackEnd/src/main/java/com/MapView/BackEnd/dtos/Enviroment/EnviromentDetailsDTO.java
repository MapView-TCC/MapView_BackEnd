package com.MapView.BackEnd.dtos.Enviroment;

import com.MapView.BackEnd.entities.Enviroment;
import com.MapView.BackEnd.entities.Raspberry;

public record EnviromentDetailsDTO(Long id_environment,String environment_name, Raspberry raspberry) {
    public EnviromentDetailsDTO(Enviroment enviroment){
        this(enviroment.getId_enviroment(),enviroment.getEnviroment_name(),enviroment.getRaspberry());
    }
}
