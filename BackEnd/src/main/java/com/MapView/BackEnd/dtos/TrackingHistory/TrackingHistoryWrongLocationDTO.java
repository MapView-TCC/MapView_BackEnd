package com.MapView.BackEnd.dtos.TrackingHistory;

import com.MapView.BackEnd.entities.*;

import java.util.List;

public record TrackingHistoryWrongLocationDTO(


        String code,
        String name_equipment,
        String wrong_location,
        String location,

        List<String> responsibles



) {


    public TrackingHistoryWrongLocationDTO(Equipment equipment, Environment environment, List<String> responsibles) {
        this(equipment.getCode(),
                equipment.getName_equipment(),
                environment.getEnvironment_name(),
                equipment.getLocation().getEnvironment().getEnvironment_name()
                ,responsibles);
    }


}

