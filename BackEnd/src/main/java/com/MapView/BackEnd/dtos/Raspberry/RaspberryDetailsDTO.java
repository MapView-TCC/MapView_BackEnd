package com.MapView.BackEnd.dtos.Raspberry;

import com.MapView.BackEnd.entities.Area;
import com.MapView.BackEnd.entities.Building;
import com.MapView.BackEnd.entities.Raspberry;

public record RaspberryDetailsDTO(
        String id_raspberry,
        Building building,
        Area area) {

    public RaspberryDetailsDTO(Raspberry raspberry){
        this(raspberry.getId_raspberry(), raspberry.getBuilding(), raspberry.getArea());
    }


}
