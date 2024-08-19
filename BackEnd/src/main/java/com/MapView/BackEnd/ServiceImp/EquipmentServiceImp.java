package com.MapView.BackEnd.ServiceImp;

import com.MapView.BackEnd.Service.EquipmentService;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.MainOwner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class EquipmentServiceImp implements EquipmentService {
    @Override
    public void getEquipment(Long id_equipment) {

    }

    @Override
    public void getAllEquipment() {

    }

    @Override
    public void createEquipment(Long id_equipment, String rfid, String type, String model, LocalDate validity, String admin_rights, String observation, Location id_location, MainOwner id_owner) {

    }

    @Override
    public void updateEquipment(String rfid, String type, String model, LocalDate validity, String admin_rights, String observation, Location id_location, MainOwner id_owner) {

    }

    @Override
    public void activateEquipment(Long id_equipment) {

    }

    @Override
    public void inactivateEquipment(Long id_equipment) {

    }
}
