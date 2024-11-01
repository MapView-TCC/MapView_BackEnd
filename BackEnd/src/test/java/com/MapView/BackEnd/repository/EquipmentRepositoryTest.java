package com.MapView.BackEnd.repository;

import com.MapView.BackEnd.dtos.Equipment.EquipmentCreateDTO;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.MainOwner;
import com.MapView.BackEnd.enums.EnumModelEquipment;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.MapView.BackEnd.serviceImp.EquipmentServiceImp.getStartDateFromQuarter;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class EquipmentRepositoryTest {

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    EntityManager entityManager;

    private Equipment createEquipment(EquipmentCreateDTO dto){
        Equipment newEquipment = new Equipment();

        newEquipment.setCode(dto.code());
        newEquipment.setName_equipment(dto.name_equipment());
        newEquipment.setRfid(dto.rfid());
        newEquipment.setType(dto.type());
        newEquipment.setModel(dto.model());
        newEquipment.setValidity(getStartDateFromQuarter(dto.validity()));
        newEquipment.setAdmin_rights(dto.admin_rights());
        newEquipment.setObservation(dto.observation());

        this.entityManager.persist(newEquipment);
        return newEquipment;
    }

    @Test
    @Transactional
    @DisplayName("Should get Equipment successfully from DB") // descrição do teste unitario
    void findByValiditySuccess() {

        Location location = new Location();
        location.setId_location(1L);

        MainOwner mainOwner = new MainOwner();
        mainOwner.setCodOwner("FCA0JA");
        mainOwner.setOperative(true);

        String validy = "2027.Q1";
        long rfid = 545645646;

        // tem o caso de existir ou não essa validade
        EquipmentCreateDTO data = new EquipmentCreateDTO("E0121548", "teste", rfid, "tipo",
                EnumModelEquipment.DESKTOP_EXTERNO, validy, "ADMIN123456", "Observação né", location.getId_location(),
                mainOwner.getId_owner());
        this.createEquipment(data);

        LocalDate validityDate = getStartDateFromQuarter(validy);
        Optional<Equipment> foundedEquipment = this.equipmentRepository.findByValidity(validityDate);

        // verificar se foi encontrado
        assertThat(foundedEquipment.isPresent()).isTrue();
    }

    @Test
    @Transactional
    @DisplayName("Should not get Equipment from DB when Equipment not exists") // descrição do teste unitario
    void findByValidityNotSuccess() {
        String validy = "2027.Q1";

        LocalDate validityDate = getStartDateFromQuarter(validy);
        Optional<Equipment> foundedEquipment = this.equipmentRepository.findByValidity(validityDate);

        // verificar se não foi encontrado
        assertThat(foundedEquipment.isEmpty()).isTrue();
    }

    @Test
    @Transactional
    @DisplayName("Must get equipment model successfully from database") // descrição do teste unitario
    void findByModelSuccess() {
        Location location = new Location();
        location.setId_location(1L);

        MainOwner mainOwner = new MainOwner();
        mainOwner.setCodOwner("FCA0JA");
        mainOwner.setOperative(true);

        EnumModelEquipment model = EnumModelEquipment.DESKTOP_EXTERNO;
        long rfid = 545645646;

        // tem o caso de existir ou não essa validade
        EquipmentCreateDTO data = new EquipmentCreateDTO("E0121548", "teste", rfid, "tipo",
                model, "2027.Q1", "ADMIN123456", "Observação né", location.getId_location(),
                mainOwner.getId_owner());
        this.createEquipment(data);

        List<Equipment> foundedEquipment = this.equipmentRepository.findByModel(model);

        // verificar se foi encontrado, ou seja, se a lista não esta vazia
        assertThat(foundedEquipment).isNotEmpty();
    }

    @Test
    @Transactional
    @DisplayName("Should not obtain the equipment model from the database when the equipment does not exist") // descrição do teste unitario
    void findByModelNotSuccess() {
        EnumModelEquipment model = EnumModelEquipment.DESKTOP_EXTERNO;

        List<Equipment> foundedEquipment = this.equipmentRepository.findByModel(model);

        // Verificar se a lista esta vazia
        assertThat(foundedEquipment).isEmpty();
    }

    @Test
    @Transactional
    @DisplayName("Must get equipment rfid successfully from database") // descrição do teste unitario
    void findByRfidSuccess() {
        Location location = new Location();
        location.setId_location(1L);

        MainOwner mainOwner = new MainOwner();
        mainOwner.setCodOwner("FCA0JA");
        mainOwner.setOperative(true);

        EnumModelEquipment model = EnumModelEquipment.DESKTOP_EXTERNO;
        long rfid = 545645646;

        // tem o caso de existir ou não essa validade
        EquipmentCreateDTO data = new EquipmentCreateDTO("E0121548", "teste", rfid, "tipo",
                model, "2027.Q1", "ADMIN123456", "Observação né", location.getId_location(),
                mainOwner.getId_owner());
        this.createEquipment(data);

        Optional<Equipment> foundedEquipment = this.equipmentRepository.findByRfid(rfid);

        // verificar se foi encontrado
        assertThat(foundedEquipment.isPresent()).isTrue();
    }

    @Test
    @Transactional
    @DisplayName("Should not obtain the equipment rfid from the database when the equipment does not exist")
    void findByRfidNotSuccess() {
        long rfid = 545645646;

        Optional<Equipment> foundedEquipment = this.equipmentRepository.findByRfid(rfid);

        // verificar se não foi encontrado
        assertThat(foundedEquipment.isEmpty()).isTrue();
    }
}