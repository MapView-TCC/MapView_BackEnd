package com.MapView.BackEnd.dtos.TrackingHistory;

import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleDetailsDTO;
import com.MapView.BackEnd.entities.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record TrackingHistoryWrongLocationDTO(


       String id_equipment,
       String name_equipment,
       String localizacao_origem,
       String Localizaçao_atual,
       List<String> responsibles



) {
    public TrackingHistoryWrongLocationDTO(Equipment equipment, Enviroment enviroment,List<String> responsibles){
        this(equipment.getIdEquipment(),equipment.getName_equipment(),enviroment.getEnvironment_name(),equipment.getLocation().getEnvironment().getEnvironment_name(),responsibles);
    }
}

