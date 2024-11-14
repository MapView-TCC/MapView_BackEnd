package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.enums.EnumModelEquipment;
import com.MapView.BackEnd.enums.EnumTrackingAction;
import com.MapView.BackEnd.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.MapView.BackEnd.serviceImp.EquipmentServiceImp.getStartDateFromQuarter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test") // configurar o banco de dados H2
@Transactional
public class EquipmentServiceImpTest {

    @InjectMocks
    private EquipmentServiceImp equipmentServiceImp;


    @Mock
    private EquipmentRepository equipmentRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private MainOwnerRepository mainOwnerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock UserLogRepository userLogRepository;


    @Mock
    private TrackingHistoryRepository trackingHistoryRepository;


    @Test
    void getEquipmentCode() {
        Long mainOwnerId = 1L;
        Long locationId = 1L;
        Long equipmentId = 1L;
        Long userLogId = 1L;
        long rfid = 545645646;
        String validityQuarter = "2027.Q1"; // Exemplo do trimestre

        // Mock para o repositório de usuários
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Mock para o repositório de MainOwner
        MainOwner mainOwner = new MainOwner();
        mainOwner.setId_owner(mainOwnerId);
        mainOwner.setCodOwner("FMA6CA");
        mainOwner.setOperative(true);
        when(mainOwnerRepository.findById(mainOwnerId)).thenReturn(Optional.of(mainOwner));

        // Mock para o repositório de Localização
        Location location = new Location();
        location.setId_location(locationId);
        when(locationRepository.findById(locationId)).thenReturn(Optional.of(location));

        // Mock para o repositório de Equipamento
        Equipment equipment = new Equipment();
        equipment.setId_equipment(equipmentId);
        equipment.setCode("CA-C 005A0");
        equipment.setName_equipment("Notebook 26");
        equipment.setRfid(rfid);
        equipment.setType("Notebook");
        equipment.setModel(EnumModelEquipment.NOTEBOOK_STANDARD);

        // Tranformar o trimestre em localdate
        equipment.setValidity(getStartDateFromQuarter(validityQuarter)); // Converte "2027.Q1" para "2027-01-01"

        equipment.setAdmin_rights("GA15231SOUT5445");
        equipment.setObservation("Ta bom");
        equipment.setLocation(location);
        equipment.setOwner(mainOwner);
        equipment.setOperative(true);
        when(equipmentRepository.findByCode("CA-C 005A0")).thenReturn(Optional.of(equipment));

        // Mock para o repositório de Histórico (TrackingHistory)
        TrackingHistory trackingHistory = new TrackingHistory();
        trackingHistory.setId(1L);
        trackingHistory.setEquipment(equipment);
        trackingHistory.setAction(EnumTrackingAction.ENTER);
        when(trackingHistoryRepository.findById(trackingHistory.getId())).thenReturn(Optional.of(trackingHistory));

        // Executando o método a ser testado
        EquipmentDetailsDTO result = equipmentServiceImp.getEquipmentCode(equipment.getCode(), userLogId);
        System.out.println(result);

        assertNotNull(result);  // Verifica se o resultado não é nulo
        assertEquals("CA-C 005A0", result.code());  // Verifica o código do equipamento
        assertEquals("Notebook 26", result.name_equipment());  // Verifica o nome do equipamento
        assertEquals(EnumModelEquipment.NOTEBOOK_STANDARD, result.model());  // Verifica o modelo do equipamento
    }


    @Test
    void getAllEquipment() {
    }

    @Test
    void createEquipment() {
    }

    @Test
    void updateEquipment() {
    }

    @Test
    void activateEquipment() {
    }

    @Test
    void inactivateEquipment() {
    }

    @Test
    void getEquipmentSearchBar() {
    }
}
