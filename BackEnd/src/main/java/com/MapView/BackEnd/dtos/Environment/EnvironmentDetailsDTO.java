package com.MapView.BackEnd.dtos.Environment;

import com.MapView.BackEnd.entities.Environment;
import com.MapView.BackEnd.entities.Raspberry;

public record EnvironmentDetailsDTO(Long id_environment, String environment_name, Raspberry raspberry) {

    public EnvironmentDetailsDTO(Environment environment){
        this(environment.getId_environment(),environment.getEnvironment_name(),environment.getRaspberry());
    }

}
