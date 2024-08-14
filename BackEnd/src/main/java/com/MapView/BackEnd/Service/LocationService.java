package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.entities.Enviroment;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Post;
import com.MapView.BackEnd.entities.Responsible;

import java.time.LocalDate;

public interface LocationService {

    void getLocation(Long id_location);
    void getAllLocation();
    void createLocation(Long id_location, Post id_post, Enviroment id_enviroment);
    void updateLocation(Post id_post, Enviroment id_enviroment);
    void activateLocation(Long id_location); // put
    void inactivateLocation(Long id_location); // put

}
