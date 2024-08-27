package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Equipment.EquipmentCreateDTO;
import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.dtos.Equipment.EquipmentUpdateDTO;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.MainOwner;

import java.time.LocalDate;
import java.util.List;

public interface EquipmentService {

    EquipmentDetailsDTO getEquipment(String id_equipment);
    List<EquipmentDetailsDTO> getAllEquipment();
    EquipmentDetailsDTO createEquipment(EquipmentCreateDTO dados);
    EquipmentDetailsDTO updateEquipment(String id_equipment, EquipmentUpdateDTO dados);
    void activateEquipment(String id_equipment); // put
    void inactivateEquipment(String id_equipment); // put

}
