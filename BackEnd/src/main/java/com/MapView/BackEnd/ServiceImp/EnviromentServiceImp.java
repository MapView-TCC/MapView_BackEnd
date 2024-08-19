package com.MapView.BackEnd.ServiceImp;

import com.MapView.BackEnd.Service.EnviromentService;
import com.MapView.BackEnd.entities.Raspberry;
import org.springframework.stereotype.Service;

@Service

public class EnviromentServiceImp implements EnviromentService {
    @Override
    public void getEnvioment(Long id_environment) {

    }

    @Override
    public void getAllEnvioment() {

    }

    @Override
    public void createEnviroment(Long id_environment, String environment_name, Raspberry id_raspberry) {

    }


    @Override
    public void updateEnviroment(String environment_name, Raspberry id_raspberry) {

    }

    @Override
    public void activateEnviroment(Long id_environment) {

    }

    @Override
    public void inactivateEnviroment(Long id_environment) {

    }
}
