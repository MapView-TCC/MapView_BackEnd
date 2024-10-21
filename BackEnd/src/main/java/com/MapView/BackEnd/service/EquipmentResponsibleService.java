package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleCreateDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleDetailsDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleUpdateDTO;

import java.util.List;

public interface EquipmentResponsibleService {

    EquipmentResponsibleDetailsDTO getEquipmentResponsible(Long id_equip_resp);
    List<EquipmentResponsibleDetailsDTO> getAllEquipmentResponsible();
    EquipmentResponsibleDetailsDTO createEquipmentResponsible(EquipmentResponsibleCreateDTO equipmentResponsibleCreateDTO);
    EquipmentResponsibleDetailsDTO updateEquipmentResponsible(Long id_equip_resp, EquipmentResponsibleUpdateDTO dados);
    void activateEquipmentResponsible(Long id_equip_resp); // put
    void inactivateEquipmentResponsible(Long id_equip_resp); // put

    List<EquipmentResponsibleDetailsDTO> getEquipmentInventory(int page, int itens, String validity, String environment, String id_owner, String id_equipment, String name_equipment, String post);
}