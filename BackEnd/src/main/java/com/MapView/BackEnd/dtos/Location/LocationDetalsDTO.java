package com.MapView.BackEnd.dtos.Location;

import com.MapView.BackEnd.entities.Environment;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.Post;

public record LocationDetalsDTO (Long id_location, Post post, Environment environment) {
    public LocationDetalsDTO(Location location){
        this(location.getId_location(), location.getPost(),location.getEnvironment());
    }
}
