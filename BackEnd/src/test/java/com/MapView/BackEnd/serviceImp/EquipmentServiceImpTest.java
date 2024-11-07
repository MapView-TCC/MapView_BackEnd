package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.enums.EnumModelEquipment;
import com.MapView.BackEnd.enums.EnumTrackingAction;
import com.MapView.BackEnd.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
    private FileStorageProperties fileStorageProperties;


    @Mock
    private ImageRepository imageRepository;

    @Mock
    private TrackingHistoryRepository trackingHistoryRepository;




    @Test
    void getEquipmentCode() {
        // Criando a instância do serviço diretamente dentro do teste
        EquipmentServiceImp equipmentServiceImp = new EquipmentServiceImp(
                entityManager,
                equipmentRepository,
                locationRepository,
                mainOwnerRepository,
                userLogRepository,
                userRepository,
                fileStorageProperties,
                trackingHistoryRepository,
                imageRepository
        );

        Long mainOwnerId = 1L;
        Long locationId = 1L;
        Long equipmentId = 1L;
        Long userLogId = 1L;
        Long imageId = 1L;
        long rfid = 545645646;
        String validity = "2027.Q1";

        // Configurando o mock para o fileStorageProperties
        when(fileStorageProperties.getUploadDir()).thenReturn("uploads");

        // Mock para o repositório de usuários
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Mock para o repositório de imagens
        Image image = new Image();
        image.setId_image(imageId);
        image.setImage("https://example.com/image.jpg");
        image.setModel(EnumModelEquipment.NOTEBOOK_STANDARD);
        when(imageRepository.findById(imageId)).thenReturn(Optional.of(image));

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
        equipment.setValidity(LocalDate.parse(validity));
        equipment.setAdmin_rights("GA15231SOUT5445");
        equipment.setObservation("Ta bom");
        equipment.setLocation(location);
        equipment.setOwner(mainOwner);
        equipment.setId_image(image);
        when(equipmentRepository.findById(equipmentId)).thenReturn(Optional.of(equipment));

        // Mock para o repositório de Histórico (TrackingHistory)
        TrackingHistory trackingHistory = new TrackingHistory();
        trackingHistory.setId(1L);
        trackingHistory.setEquipment(equipment);
        trackingHistory.setAction(EnumTrackingAction.ENTER);
        when(trackingHistoryRepository.findById(trackingHistory.getId())).thenReturn(Optional.of(trackingHistory));

        // Executando o método a ser testado
        EquipmentDetailsDTO result = equipmentServiceImp.getEquipmentCode(equipment.getCode(), userLogId);

        // Asserções (exemplo)
        assertNotNull(result);
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
