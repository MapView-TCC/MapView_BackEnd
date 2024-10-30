package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Equipment.*;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.MainOwner;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EquipmentService {

    EquipmentDetailsDTO getEquipment(String code, Long userLog_id);
    List<EquipmentDetailsDTO> getAllEquipment(int page, int itens, Long userLog_id);
    EquipmentDetailsDTO createEquipment(EquipmentCreateDTO dados, Long userLog_id);
    EquipmentDetailsDTO updateEquipment(Long id_equipment, EquipmentUpdateDTO dados, Long userLog_id);
    void activateEquipment(String code, Long userLog_id); // put
    void inactivateEquipment(String code, Long userLog_id); // put

    // tentativa dos filtros
    //List<EquipmentFilterDTO> getEquipmentInventory(int page, int itens, String validity, String environment, String id_owner, String id_equipment, String name_equipment, String post);
    List<EquipmentSearchBarDTO> getEquipmentSearchBar(String searchTerm);
}
