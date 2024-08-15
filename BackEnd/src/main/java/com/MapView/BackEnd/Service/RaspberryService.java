package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.entities.Area;
import com.MapView.BackEnd.entities.Building;

public interface RaspberryService {

    void getRaspberry(Long id_Raspberry);
    void getAllRaspberry();
    void createRaspberry(String Raspberry_name, Building id_building, Area id_area);
    void updateRaspberry(String Raspberry);
    void activeRaspberry(Long id_Raspberry);
    void inactivateEnviroment(Long id_Raspberry);
}
