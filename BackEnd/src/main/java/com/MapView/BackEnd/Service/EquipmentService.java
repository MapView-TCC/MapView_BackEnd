package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.MainOwner;

import java.time.LocalDate;

public interface EquipmentService {

    void getEquipment(Long id_equipment);
    void getAllEquipment();
    void createEquipment(Long id_equipment, String rfid, String type, String model, LocalDate validity, String admin_rights, String observation, Location id_location, MainOwner id_owner);
    void updateEquipment(String rfid, String type, String model, LocalDate validity, String admin_rights, String observation, Location id_location, MainOwner id_owner);
    void activateEquipment(Long id_equipment); // put
    void inactivateEquipment(Long id_equipment); // put

}
