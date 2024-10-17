package com.MapView.BackEnd.dtos.Equipment;

import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.enums.EnumModelEquipment;

import java.time.LocalDate;
import java.util.List;

public record EquipmentSearchBarDTO(String id_equipment,
                                     String name_equipment,
                                     String location,
                                     String owner,
                                     String environment,
                                     List<String> responsibles ) {

    public EquipmentSearchBarDTO(Equipment equipment, Location location, MainOwner mainOwner, Environment environment, List<String> responsibles){
        this(equipment.getIdEquipment(), equipment.getName_equipment(), location.getPost().getPost(),
                mainOwner.getId_owner(), environment.getEnvironment_name(), responsibles);
    }

}
