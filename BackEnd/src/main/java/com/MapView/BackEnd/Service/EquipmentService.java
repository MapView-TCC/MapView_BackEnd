package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.dtos.Equipment.EquipmentCreateDTO;
import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.MainOwner;

import java.time.LocalDate;

public interface EquipmentService {

    void getEquipment(Long id_equipment);
    void getAllEquipment();
    EquipmentDetailsDTO createEquipment(EquipmentCreateDTO dados);
    void updateEquipment(String rfid, String type, String model, LocalDate validity, String admin_rights, String observation, Location id_location, MainOwner id_owner);
    void activateEquipment(String id_equipment); // put
    void inactivateEquipment(String id_equipment); // put

}
