package com.MapView.BackEnd.dtos.Location;

import com.MapView.BackEnd.entities.Environment;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.Post;

public record LocationDetailsDTO(Long id_location, Post post, Environment environment) {
    public LocationDetailsDTO(Location location){
        this(location.getId_location(), location.getPost(),location.getEnvironment());
    }
}
