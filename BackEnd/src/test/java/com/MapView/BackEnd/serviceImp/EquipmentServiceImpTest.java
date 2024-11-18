package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Equipment.EquipmentCreateDTO;
import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.dtos.Equipment.EquipmentUpdateDTO;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.MapView.BackEnd.serviceImp.EquipmentServiceImp.getStartDateFromQuarter;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Mock
    private UserLogRepository userLogRepository;


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
        Long mainOwnerId = 1L;
        Long locationId = 1L;
        Long equipmentId = 1L;
        Long userLogId = 1L;
        long rfid = 545645646;
        String validityQuarter = "2027.Q1";

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
        equipment.setValidity(getStartDateFromQuarter(validityQuarter)); // Converte "2027.Q1" para "2027-01-01"
        equipment.setAdmin_rights("GA15231SOUT5445");
        equipment.setObservation("Ta bom");
        equipment.setLocation(location);
        equipment.setOwner(mainOwner);
        equipment.setOperative(true);

        when(equipmentRepository.findAllByOperativeTrue(PageRequest.of(0, 100))).thenReturn(Collections.singletonList(equipment));

        // Execução do teste
        List<EquipmentDetailsDTO> result = equipmentServiceImp.getAllEquipment(0, 100, userLogId);
        System.out.println(result);

        // Verificações
        assertNotNull(result, "A lista de equipamentos não deveria ser nula");
        assertEquals(1, result.size(), "Deveria haver apenas um equipamento na lista");

        EquipmentDetailsDTO dto = result.get(0);

        assertEquals("CA-C 005A0", dto.code(), "O código do equipamento está incorreto");
        assertEquals("Notebook 26", dto.name_equipment(), "O nome do equipamento está incorreto");
        assertEquals(rfid, dto.rfid(), "O RFID do equipamento está incorreto");
        assertEquals("Notebook", dto.type(), "O tipo do equipamento está incorreto");
        assertEquals("2027.Q1", dto.validity(), "A validade do equipamento está incorreta");
        assertEquals("FMA6CA", dto.owner().getCodOwner(), "O código do proprietário está incorreto");
        assertEquals("GA15231SOUT5445", dto.admin_rights(), "Os direitos de administrador estão incorretos");
        assertEquals("Ta bom", dto.observation(), "A observação do equipamento está incorreta");
    }

    @Test
    void createEquipment() {
        Long mainOwnerId = 1L;
        Long locationId = 1L;
        Long equipmentId = 1L;
        Long userLogId = 1L;
        long rfid = 545645646;
        String validityQuarter = "2027.Q1";

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

        EquipmentCreateDTO createDTO = new EquipmentCreateDTO("CA-C 005A0", "Notebook 26",
                rfid, "Notebook", EnumModelEquipment.DESKTOP_EXTERNO, validityQuarter, "GA15231SOUT5445",
                "Ta bom", locationId, mainOwnerId);

        // Mock para o repositório de Equipamento
        Equipment equipment = new Equipment();
        equipment.setId_equipment(equipmentId);
        equipment.setCode(createDTO.code());
        equipment.setName_equipment(createDTO.name_equipment());
        equipment.setRfid(createDTO.rfid());
        equipment.setType(createDTO.type());
        equipment.setModel(createDTO.model());
        equipment.setValidity(getStartDateFromQuarter(createDTO.validity())); // Converte "2027.Q1" para "2027-01-01"
        equipment.setAdmin_rights(createDTO.admin_rights());
        equipment.setObservation(createDTO.observation());
        equipment.setLocation(location);
        equipment.setOwner(mainOwner);
        equipment.setOperative(true);
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment);

        EquipmentDetailsDTO result = equipmentServiceImp.createEquipment(createDTO, userLogId);
        System.out.println(result);

        assertNotNull(result);
        assertEquals(locationId, createDTO.id_location());
        assertEquals("CA-C 005A0", equipment.getCode());
    }

    @Test
    void updateEquipment() {
        Long mainOwnerId = 1L;
        Long locationId = 1L;
        Long equipmentId = 1L;
        Long userLogId = 1L;
        long rfid = 545645646;
        String validityQuarter = "2027.Q1";


        EquipmentUpdateDTO updateDTO = new EquipmentUpdateDTO(
                "CA-C 005A0",
                "Notebook Updated",
                rfid,
                "Notebook",
                EnumModelEquipment.NOTEBOOK_STANDARD,
                validityQuarter,
                "GA15231SOUT5445_UPDATED",
                "Updated observation",
                locationId, // ID da localização
                mainOwnerId // ID do proprietário
        );

        // Mock para equipamento
        Equipment equipment = new Equipment();
        equipment.setId_equipment(equipmentId);
        equipment.setCode("CA-C 005A0");
        equipment.setName_equipment("Notebook Original");
        equipment.setRfid(rfid);
        equipment.setType("Laptop");
        equipment.setModel(EnumModelEquipment.NOTEBOOK_STANDARD);
        equipment.setValidity(LocalDate.of(2027, 1, 1)); // Validade inicial
        equipment.setAdmin_rights("GA15231SOUT5445");
        equipment.setObservation("Original observation");
        equipment.setOperative(true);

        when(equipmentRepository.findByCode(updateDTO.code())).thenReturn(Optional.of(equipment));

        // Mock para usuário
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Mock para localização
        Location location = new Location();
        location.setId_location(updateDTO.id_location());
        when(locationRepository.findById(updateDTO.id_location())).thenReturn(Optional.of(location));

        // Mock para proprietário
        MainOwner owner = new MainOwner();
        owner.setId_owner(updateDTO.id_owner());
        when(mainOwnerRepository.findById(updateDTO.id_owner())).thenReturn(Optional.of(owner));

        // Mock para salvar logs
        when(userLogRepository.save(any(UserLog.class))).thenReturn(new UserLog());

        // Mock para salvar equipamento
        when(equipmentRepository.save(any(Equipment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Chamada ao método
        EquipmentDetailsDTO result = equipmentServiceImp.updateEquipment(equipmentId, updateDTO, userLogId);

        // Verificações
        assertNotNull(result, "O equipamento atualizado não deveria ser nulo");
        assertEquals("Notebook Updated", result.name_equipment(), "O nome do equipamento não foi atualizado corretamente");
        assertEquals("Notebook", result.type(), "O tipo do equipamento não foi atualizado corretamente");
        assertEquals("GA15231SOUT5445_UPDATED", result.admin_rights(), "Os direitos de administrador não foram atualizados corretamente");
        assertEquals("Updated observation", result.observation(), "A observação não foi atualizada corretamente");
        assertEquals(location.getId_location(), result.location().getId_location(), "A localização não foi atualizada corretamente");
        assertEquals(owner.getId_owner(), result.owner().getId_owner(), "O proprietário não foi atualizado corretamente");
    }

    @Test
    void activateEquipment() {
        String equipmentCode = "CA-C 005A0";
        Long userLogId = 1L;

        // Mock usuário
        Users user = new Users();
        user.setId_user(userLogId);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Mock equipamento
        Equipment equipment = new Equipment();
        equipment.setCode(equipmentCode);
        equipment.setOperative(false); // Inicialmente inativo
        when(equipmentRepository.findByCode(equipmentCode)).thenReturn(Optional.of(equipment));

        // Mock salvar
        when(equipmentRepository.save(any(Equipment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(userLogRepository.save(any(UserLog.class))).thenReturn(new UserLog());

        // Chamada ao método
        equipmentServiceImp.activateEquipment(equipmentCode, userLogId);

        // Verificações
        assertTrue(equipment.isOperative(), "O equipamento deveria estar ativo após ativação");
        verify(equipmentRepository, times(1)).save(equipment);
        verify(userLogRepository, times(1)).save(any(UserLog.class));
    }

    @Test
    void inactivateEquipment() {
        String equipmentCode = "CA-C 005A0";
        Long userLogId = 1L;

        // Mock usuário
        Users user = new Users();
        user.setId_user(userLogId);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Mock equipamento
        Equipment equipment = new Equipment();
        equipment.setCode(equipmentCode);
        equipment.setOperative(true); // Inicialmente ativo
        when(equipmentRepository.findByCode(equipmentCode)).thenReturn(Optional.of(equipment));

        // Mock salvar
        when(equipmentRepository.save(any(Equipment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(userLogRepository.save(any(UserLog.class))).thenReturn(new UserLog());

        // Chamada ao método
        equipmentServiceImp.inactivateEquipment(equipmentCode, userLogId);

        // Verificações
        assertFalse(equipment.isOperative(), "O equipamento deveria estar inativo após inativação");
        assertNull(equipment.getLocation(), "A localização deveria ser null após inativação");
        verify(equipmentRepository, times(1)).save(equipment);
        verify(userLogRepository, times(1)).save(any(UserLog.class));
    }


    @Test
    void testGetStartDateFromQuarter() {

        // Teste para o primeiro trimestre (Q1)
        LocalDate resultQ1 = EquipmentServiceImp.getStartDateFromQuarter("2021.Q1");
        assertEquals(LocalDate.of(2021, 1, 1), resultQ1, "A data para o Q1 deve ser 1º de Janeiro de 2021");
        System.out.println(resultQ1);

        // Teste para o segundo trimestre (Q2)
        LocalDate resultQ2 = EquipmentServiceImp.getStartDateFromQuarter("2021.Q2");
        assertEquals(LocalDate.of(2021, 4, 1), resultQ2, "A data para o Q2 deve ser 1º de Abril de 2021");
        System.out.println(resultQ2);

        // Teste para o terceiro trimestre (Q3)
        LocalDate resultQ3 = EquipmentServiceImp.getStartDateFromQuarter("2021.Q3");
        assertEquals(LocalDate.of(2021, 7, 1), resultQ3, "A data para o Q3 deve ser 1º de Julho de 2021");
        System.out.println(resultQ3);

        // Teste para o quarto trimestre (Q4)
        LocalDate resultQ4 = EquipmentServiceImp.getStartDateFromQuarter("2021.Q4");
        assertEquals(LocalDate.of(2021, 10, 1), resultQ4, "A data para o Q4 deve ser 1º de Outubro de 2021");
        System.out.println(resultQ4);

    }

    // Teste do método getQuarterStringFromDate
    @Test
    void testGetQuarterStringFromDate() {
        // Teste para janeiro (Q1)
        String resultQ1 = EquipmentServiceImp.getQuarterStringFromDate(LocalDate.of(2021, 1, 1));
        assertEquals("2021.Q1", resultQ1, "Deve retornar '2021.Q1' para janeiro");
        System.out.println(resultQ1);

        // Teste para abril (Q2)
        String resultQ2 = EquipmentServiceImp.getQuarterStringFromDate(LocalDate.of(2021, 4, 1));
        assertEquals("2021.Q2", resultQ2, "Deve retornar '2021.Q2' para abril");
        System.out.println(resultQ2);

        // Teste para julho (Q3)
        String resultQ3 = EquipmentServiceImp.getQuarterStringFromDate(LocalDate.of(2021, 7, 1));
        assertEquals("2021.Q3", resultQ3, "Deve retornar '2021.Q3' para julho");
        System.out.println(resultQ3);

        // Teste para outubro (Q4)
        String resultQ4 = EquipmentServiceImp.getQuarterStringFromDate(LocalDate.of(2021, 10, 1));
        assertEquals("2021.Q4", resultQ4, "Deve retornar '2021.Q4' para outubro");
        System.out.println(resultQ4);
    }

}