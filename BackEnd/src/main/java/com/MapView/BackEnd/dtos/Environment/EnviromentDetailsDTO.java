package com.MapView.BackEnd.dtos.Environment;

import com.MapView.BackEnd.entities.Enviroment;
import com.MapView.BackEnd.entities.Raspberry;

public record EnviromentDetailsDTO(Long id_enviroment,String enviroment_name, Raspberry raspberry) {

    public EnviromentDetailsDTO(Enviroment environment){
        this(environment.getId_enviroment(),environment.getEnviroment_name(),environment.getRaspberry());
    }

}
