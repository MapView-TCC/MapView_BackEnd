package com.MapView.BackEnd.dtos.Area;

import com.MapView.BackEnd.entities.Area;

public record AreaDetailsDTO(Long id_area, String area_code, String area_name) {
    public AreaDetailsDTO(Area area){
        this(area.getId_area(), area.getCode(), area.getArea_name());
    }
}
