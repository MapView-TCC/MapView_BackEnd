package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Equipment.EquipmentUpdateDTO;
import com.MapView.BackEnd.infra.NotFoundException;
import com.MapView.BackEnd.repository.EquipmentRepository;
import com.MapView.BackEnd.repository.LocationRepository;
import com.MapView.BackEnd.repository.MainOwnerRepository;
import com.MapView.BackEnd.service.EquipmentService;
import com.MapView.BackEnd.dtos.Equipment.EquipmentCreateDTO;
import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.MainOwner;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentServiceImp implements EquipmentService {


    private final EquipmentRepository equipmentRepository ;
    private final LocationRepository locationRepository;
    private final MainOwnerRepository mainOwnerRepository;

    public EquipmentServiceImp(EquipmentRepository equipmentRepository, LocationRepository locationRepository, MainOwnerRepository mainOwnerRepository) {
        this.equipmentRepository = equipmentRepository;
        this.locationRepository = locationRepository;
        this.mainOwnerRepository = mainOwnerRepository;
    }


    @Override
    public EquipmentDetailsDTO getEquipment(String id_equipment) {
        var equipment = equipmentRepository.findById(String.valueOf(id_equipment)).orElseThrow(() -> new NotFoundException("Id equipment not found!"));

        if (!equipment.status_check()){
            return null;
        }

        return new EquipmentDetailsDTO(equipment);
    }

    @Override
    public List<EquipmentDetailsDTO> getAllEquipment(int page, int itens) {
        return equipmentRepository.findAllByOperativeTrue(PageRequest.of(page, itens)).stream().map(EquipmentDetailsDTO::new).toList();
    }


    @Override
    public EquipmentDetailsDTO createEquipment(EquipmentCreateDTO dados) {
        // location
        Location location = locationRepository.findById(Long.valueOf(dados.id_location()))
                .orElseThrow(() -> new RuntimeException("Id location Não encontrado!"));

        // main owner
        MainOwner mainOwner = mainOwnerRepository.findById(String.valueOf(dados.id_owner()))
                .orElseThrow(() -> new RuntimeException("Id main owner Não encontrado"));


        Equipment equipment = new Equipment(dados,location,mainOwner);

        equipmentRepository.save(equipment);

        return new EquipmentDetailsDTO(equipment);
    }

    @Override
    public EquipmentDetailsDTO updateEquipment(String id_equipment, EquipmentUpdateDTO dados) {
        var equipment = equipmentRepository.findById(id_equipment)
                .orElseThrow(() -> new NotFoundException("Id not found"));

        if (dados.rfid() != null) {
            equipment.setRfid(dados.rfid());
        }

        if (dados.type() != null) {
            equipment.setType(dados.type());
        }

        if (dados.model() != null) {
            equipment.setModel(dados.model());
        }

        if (dados.validity() != null) {
            equipment.setValidity(dados.validity());
        }

        if (dados.admin_rights() != null) {
            equipment.setAdmin_rights(dados.admin_rights());
        }

        if (dados.observation() != null) {
            equipment.setObservation(dados.observation());
        }

        if (dados.id_location() != null) {
            var location = locationRepository.findById(dados.id_location())
                    .orElseThrow(() -> new NotFoundException("Location id not found"));
            equipment.setId_location(location);
        }

        if (dados.id_owner() != null) {
            var owner = mainOwnerRepository.findById(dados.id_owner())
                    .orElseThrow(() -> new NotFoundException("Owner id not found"));
            equipment.setId_owner(owner);
        }

        // Salva a entidade atualizada no repositório
        equipmentRepository.save(equipment);
        return new EquipmentDetailsDTO(equipment);
    }

    @Override
    public void activateEquipment(String id_equipment) {
        var equipmentClass = equipmentRepository.findById(id_equipment);
        if (equipmentClass.isPresent()){
            var equipment = equipmentClass.get();
            equipment.setOperative(true);
        }
    }

    @Override
    public void inactivateEquipment(String id_equipment) {
        var equipmentClass = equipmentRepository.findById(id_equipment);
        if (equipmentClass.isPresent()){
            var equipment = equipmentClass.get();
            equipment.setOperative(false);
        }
    }

    @Override
    public List<EquipmentDetailsDTO> getEquipmentValidation(int page, int itens, String validity, String environment, String mainOwner, String id_owner, String id_equipment) {

        List<Equipment> filteredEquipments = equipmentRepository.findAllByOperativeTrue(PageRequest.of(page, itens))
                .stream()
                .filter(e -> (validity == null || e.getValidity().equals(validity)) &&
                        (environment == null || e.getId_location().getEnvironment().getEnvironment_name().equals(environment)) &&
                        (mainOwner == null || e.getId_owner().getOwner_name().equals(mainOwner)) &&
                        (id_owner == null || e.getId_owner().getId_owner().equals(id_owner)) &&
                        (id_equipment == null || e.getId_equipment().equals(id_equipment)))
                .toList();


        if (validity == null && environment == null && mainOwner == null && id_owner == null && id_equipment == null) {
            return equipmentRepository.findAllByOperativeTrue(PageRequest.of(page, itens))
                    .stream()
                    .map(EquipmentDetailsDTO::new)
                    .collect(Collectors.toList());
        }


        return filteredEquipments.stream()
                .map(EquipmentDetailsDTO::new)
                .collect(Collectors.toList());
    }


}
