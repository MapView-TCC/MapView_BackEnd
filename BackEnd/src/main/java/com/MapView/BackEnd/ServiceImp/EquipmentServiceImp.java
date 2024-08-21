package com.MapView.BackEnd.ServiceImp;

import com.MapView.BackEnd.Repository.EquipmentRepository;
import com.MapView.BackEnd.Repository.LocationRepository;
import com.MapView.BackEnd.Repository.MainOwnerRepository;
import com.MapView.BackEnd.Service.EquipmentService;
import com.MapView.BackEnd.dtos.Equipment.EquipmentCreateDTO;
import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.MainOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class EquipmentServiceImp implements EquipmentService {

    @Autowired
    private EquipmentRepository repository ;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private MainOwnerRepository mainOwnerRepository;


    @Override
    public void getEquipment(Long id_equipment) {

    }

    @Override
    public void getAllEquipment() {

    }

    @Override
    public EquipmentDetailsDTO createEquipment(EquipmentCreateDTO dados) {
        // location
        Location location = locationRepository.findById(dados.id_location())
                .orElseThrow(() -> new RuntimeException("Não encontrado!"));

        // main owner
        MainOwner mainOwner = mainOwnerRepository.findById(String.valueOf(dados.id_owner()))
                .orElseThrow(() -> new RuntimeException("Não encontrado"));


        Equipment equipment = new Equipment(dados,location,mainOwner);

        repository.save(equipment);

        return new EquipmentDetailsDTO(equipment);
    }

    @Override
    public void updateEquipment(String rfid, String type, String model, LocalDate validity, String admin_rights, String observation, Location id_location, MainOwner id_owner) {

    }

    @Override
    public void activateEquipment(String id_equipment) {
        var equipmentClass = repository.findById(id_equipment);
        if (equipmentClass.isPresent()){
            var equipment = equipmentClass.get();
            equipment.setOperative(true);
        }
    }

    @Override
    public void inactivateEquipment(String id_equipment) {
        var equipmentClass = repository.findById(id_equipment);
        if (equipmentClass.isPresent()){
            var equipment = equipmentClass.get();
            equipment.setOperative(false);
        }
    }
}
