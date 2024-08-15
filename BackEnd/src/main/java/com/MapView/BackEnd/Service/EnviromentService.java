package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.entities.Raspberry;

public interface EnviromentService {

    void getEnvioment(Long id_environment);
    void getAllEnvioment();
    void createEnviroment(Long id_environment, String environment_name, Raspberry id_raspberry);
    void updateEnviroment(String environment_name, Raspberry id_raspberry);
    void activateEnviroment(Long id_environment); // put
    void inactivateEnviroment(Long id_environment); // put
}
