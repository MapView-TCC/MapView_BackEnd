package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Equipment.EquipmentCreateDTO;
import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.dtos.Equipment.EquipmentUpdateDTO;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.MainOwner;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface EquipmentService {

    EquipmentDetailsDTO getEquipment(String id_equipment);
    List<EquipmentDetailsDTO> getAllEquipment(int page, int itens);
    EquipmentDetailsDTO createEquipment(EquipmentCreateDTO dados);
    EquipmentDetailsDTO updateEquipment(String id_equipment, EquipmentUpdateDTO dados);
    void activateEquipment(String id_equipment); // put
    void inactivateEquipment(String id_equipment); // put

    // tentativa dos filtros
    List<EquipmentDetailsDTO> getEquipmentValidation(int page, int itens, String validity,String eviroment,String mainowner, String id_owner, String id_equipment);

}
