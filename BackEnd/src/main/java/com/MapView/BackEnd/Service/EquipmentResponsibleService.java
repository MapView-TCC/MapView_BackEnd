package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.MainOwner;
import com.MapView.BackEnd.entities.Responsible;

import java.time.LocalDate;

public interface EquipmentResponsibleService {

    void getEquipmentResponsible(Long id_equip_resp);
    void getAllEquipmentResponsible();
    void createEquipmentResponsible(Long id_equip_resp, Equipment id_equipment, Responsible id_responsible, LocalDate start_usage, LocalDate end_usage);
    void updateEquipmentResponsible(Equipment id_equipment, Responsible id_responsible, LocalDate start_usage, LocalDate end_usage);
    void activateEquipmentResponsible(Long id_equip_resp); // put
    void inactivateEquipmentResponsible(Long id_equip_resp); // put

}
