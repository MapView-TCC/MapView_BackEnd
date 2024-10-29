package com.MapView.BackEnd.dtos.Equipment;

import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.enums.EnumModelEquipment;

import java.time.LocalDate;
import java.util.List;

public record EquipmentSearchBarDTO(String code,
                                    String name_equipment,
                                    String location,
                                    String owner,
                                    String environment,
                                    String currentEnvironment,
                                    List<String> responsibles ) {

    public EquipmentSearchBarDTO(Equipment equipment, Location location, MainOwner mainOwner, Environment environment, String currentEnvironment, List<String> responsibles){
        this(equipment.getCode(), equipment.getName_equipment(), location.getPost().getPost(),
                mainOwner.getCod_owner(), environment.getEnvironment_name(), currentEnvironment, responsibles);
    }

}
