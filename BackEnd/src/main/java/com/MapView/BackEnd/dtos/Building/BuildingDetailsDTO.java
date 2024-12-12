package com.MapView.BackEnd.dtos.Building;

import com.MapView.BackEnd.entities.Building;

public record BuildingDetailsDTO(Long id_building, String building_code) {
    public BuildingDetailsDTO(Building building) {
        this(building.getId_building(), building.getBuilding_code());
    }
}
