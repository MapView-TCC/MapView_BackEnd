package com.MapView.BackEnd.dtos.Raspberry;

import com.MapView.BackEnd.entities.Area;
import com.MapView.BackEnd.entities.Building;
import com.MapView.BackEnd.entities.Raspberry;

public record RaspberryDetailsDTO(
        Long id_raspberry,
        String raspberry_name,
        Building id_building,
        Area id_area,
        boolean operative) {

    public RaspberryDetailsDTO(Raspberry raspberry){
        this(raspberry.getId_raspberry(), raspberry.getRaspberry_name(), raspberry.getId_building(), raspberry.getId_area(), raspberry.isOperative());
    }


}
