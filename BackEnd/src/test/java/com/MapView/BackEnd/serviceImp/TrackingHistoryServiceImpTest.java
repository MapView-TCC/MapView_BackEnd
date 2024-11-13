package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryCreateDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryDetailsDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryWrongLocationDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryResponsibleDetails;
import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.enums.EnumTrackingAction;
import com.MapView.BackEnd.enums.EnumWarnings;
import com.MapView.BackEnd.repository.EnvironmentRepository;
import com.MapView.BackEnd.repository.EquipmentRepository;
import com.MapView.BackEnd.repository.EquipmentResponsibleRepository;
import com.MapView.BackEnd.repository.TrackingHistoryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TrackingHistoryServiceImpTest {
    @InjectMocks
    private TrackingHistoryServiceImp trackingHistoryServiceImp;

    @Mock
    private TrackingHistoryRepository trackingHistoryRepository;

    @Mock
    private EnvironmentRepository environmentRepository;

    @Mock
    private EquipmentRepository equipmentRepository;

    @Mock
    private EquipmentResponsibleRepository equipmentResponsibleRepository;

    @Mock
    private SimpMessagingTemplate template;

    @Test
    void getTrackingHistory() {
        Long environmentId = 1L;
        Long equipmentId = 1L;
        Long trackingId = 1L;

        Equipment equipment = new Equipment();
        equipment.setId_equipment(equipmentId);
        equipment.setCode("CA-C 005A0");
        equipment.setName_equipment("Notebook 26");
        equipment.setOperative(true);
        when(equipmentRepository.findByCode(equipment.getCode())).thenReturn(Optional.of(equipment));

        Environment environment = new Environment();
        environment.setId_environment(environmentId);
        environment.setEnvironment_name("BTC");
        environment.setOperative(true);
        when(environmentRepository.findById(environment.getId_environment())).thenReturn(Optional.of(environment));

        TrackingHistory trackingHistory = new TrackingHistory();
        trackingHistory.setId(trackingId);
        trackingHistory.setEquipment(equipment);
        trackingHistory.setEnvironment(environment);
        trackingHistory.setAction(EnumTrackingAction.ENTER);
        trackingHistory.setWarning(EnumWarnings.YELLOW);
        trackingHistory.setDatetime(LocalDateTime.now());
        when(trackingHistoryRepository.findById(trackingId)).thenReturn(Optional.of(trackingHistory));

        TrackingHistoryDetailsDTO result = trackingHistoryServiceImp.getTrackingHistory(trackingId);
        System.out.println(result);

        // verificações
        assertNotNull(result);
        assertEquals(trackingId, result.id_tracking());
        assertEquals(equipment.getCode(), result.equipment().getCode());
        assertEquals(environment.getEnvironment_name(), result.environment().getEnvironment_name());
        assertEquals(EnumTrackingAction.ENTER, result.action());
        assertEquals(EnumWarnings.YELLOW, result.warning());
    }


    @Test
    void getAllTrackingHistory() {
        Long environmentId = 1L;
        Long equipmentId = 1L;
        Long trackingId = 1L;

        Equipment equipment = new Equipment();
        equipment.setId_equipment(equipmentId);
        equipment.setCode("CA-C 005A0");
        equipment.setName_equipment("Notebook 26");
        equipment.setOperative(true);
        when(equipmentRepository.findByCode(equipment.getCode())).thenReturn(Optional.of(equipment));

        Environment environment = new Environment();
        environment.setId_environment(environmentId);
        environment.setEnvironment_name("BTC");
        environment.setOperative(true);
        when(environmentRepository.findById(environment.getId_environment())).thenReturn(Optional.of(environment));

        TrackingHistory trackingHistory = new TrackingHistory();
        trackingHistory.setId(trackingId);
        trackingHistory.setEquipment(equipment);
        trackingHistory.setEnvironment(environment);
        trackingHistory.setAction(EnumTrackingAction.ENTER);
        trackingHistory.setWarning(EnumWarnings.YELLOW);
        trackingHistory.setDatetime(LocalDateTime.now());
        when(trackingHistoryRepository.findAll()).thenReturn(Collections.singletonList(trackingHistory));

        List<TrackingHistoryDetailsDTO> result = trackingHistoryServiceImp.getAllTrackingHistory();

        // verificações
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trackingId, result.get(0).id_tracking());
        assertEquals(equipment.getCode(), result.get(0).equipment().getCode());
        assertEquals(environment.getEnvironment_name(), result.get(0).environment().getEnvironment_name());
        assertEquals(EnumTrackingAction.ENTER, result.get(0).action());
        assertEquals(EnumWarnings.YELLOW, result.get(0).warning());
    }

    @Test
    void createTrackingHistory() {
        Long environmentId = 1L;
        Long equipmentId = 1L;
        Long trackingId = 1L;
        long rfid = 545645646;

        Environment environment = new Environment();
        environment.setId_environment(environmentId);
        environment.setEnvironment_name("BTC");
        environment.setOperative(true);
        when(environmentRepository.findById(environment.getId_environment())).thenReturn(Optional.of(environment));

        TrackingHistoryCreateDTO createDTO = new TrackingHistoryCreateDTO(rfid, environmentId);
        when(equipmentRepository.findByRfid(createDTO.rfid())).thenReturn(Optional.empty());

        Equipment equipment = new Equipment();
        equipment.setId_equipment(equipmentId);
        equipment.setCode("CA-C 005A0");
        equipment.setName_equipment("Notebook 26");
        equipment.setRfid(createDTO.rfid());
        equipment.setOperative(true);

        // Configura para salvar o novo equipamento criado
        when(equipmentRepository.save(any(Equipment.class))).thenReturn(equipment);

        TrackingHistory trackingHistory = new TrackingHistory();
        trackingHistory.setId(trackingId);
        trackingHistory.setEquipment(equipment);
        trackingHistory.setEnvironment(environment);
        trackingHistory.setAction(EnumTrackingAction.ENTER);
        trackingHistory.setWarning(EnumWarnings.RED);
        trackingHistory.setDatetime(LocalDateTime.now());

        // Configura para salvar e retornar o trackingHistory criado
        when(trackingHistoryRepository.save(any(TrackingHistory.class))).thenReturn(trackingHistory);

        TrackingHistoryResponsibleDetails result = trackingHistoryServiceImp.createTrackingHistory(createDTO);

        System.out.println(result);

        // Verificações
        assertNotNull(result);
        assertEquals(EnumTrackingAction.ENTER, result.action());
        assertEquals(EnumWarnings.RED, result.warning());
        assertEquals(createDTO.rfid(), result.equipment().getRfid());
        assertEquals(environment.getEnvironment_name(), result.environment().getEnvironment_name());

        // Verifica se o equipamento foi salvo no repositório
        verify(equipmentRepository).save(any(Equipment.class));

        // Verifica se o histórico foi salvo e a mensagem foi enviada
        verify(trackingHistoryRepository, times(2)).save(any(TrackingHistory.class));
        verify(template).convertAndSend(eq("/equip"), any(TrackingHistory.class));
    }

    @Test
    void filterTracking() {
        int page = 0;
        int items = 10;
        EnumTrackingAction action = EnumTrackingAction.ENTER;
        Integer day = 30;
        Integer month = 8;
        Integer year = 2023;
        EnumWarnings color = EnumWarnings.RED;
        String code = "EQ-123";

        Equipment equipment = new Equipment();
        equipment.setCode(code);

        TrackingHistory tracking1 = new TrackingHistory();
        tracking1.setAction(action);
        tracking1.setWarning(color);
        tracking1.setDatetime(LocalDateTime.of(2023, 8, 30, 10, 0));
        tracking1.setEquipment(equipment);

        TrackingHistory tracking2 = new TrackingHistory();
        tracking2.setAction(EnumTrackingAction.OUT);
        tracking2.setWarning(EnumWarnings.GREEN);
        tracking2.setDatetime(LocalDateTime.of(2023, 8, 30, 10, 0));
        tracking2.setEquipment(equipment);

        Page<TrackingHistory> pageTrackingHistory = new PageImpl<>(Arrays.asList(tracking1, tracking2));

        // Mock do repositório de histórico de rastreamento
        when(trackingHistoryRepository.findAll(PageRequest.of(page, items))).thenReturn(pageTrackingHistory);

        // Executa o método
        List<TrackingHistoryDetailsDTO> result = trackingHistoryServiceImp.FilterTracking(page, items, action, day, month, year, color, code);

        // Verificações
        assertNotNull(result);
        assertEquals(1, result.size());  // Apenas o tracking1 deve corresponder aos critérios de filtro
        TrackingHistoryDetailsDTO dto = result.get(0);
        assertEquals(action, dto.action());
        assertEquals(color, dto.warning());
        assertEquals(code, dto.equipment().getCode());
        assertEquals(30, dto.datetime().getDayOfMonth());
        assertEquals(8, dto.datetime().getMonthValue());
        assertEquals(2023, dto.datetime().getYear());

        // Verifica se o repositório foi chamado corretamente
        verify(trackingHistoryRepository).findAll(PageRequest.of(page, items));
    }

    @Test
    void findWrongLocationEquipments() {
        Long environmentId = 1L;
        Environment environment = new Environment();
        environment.setId_environment(environmentId);
        environment.setEnvironment_name("Main Environment");

        Long wrongEnvironmentId = 2L;
        Environment wrongEnvironment = new Environment();
        wrongEnvironment.setId_environment(wrongEnvironmentId);
        wrongEnvironment.setEnvironment_name("Wrong Environment");

        when(environmentRepository.findById(environmentId)).thenReturn(Optional.of(environment));

        // criando os ambientes

        Equipment equipment1 = new Equipment();
        equipment1.setId_equipment(1L);
        equipment1.setCode("EQ-1234");
        equipment1.setName_equipment("Equipment 1");

        Equipment equipment2 = new Equipment();
        equipment2.setId_equipment(2L);
        equipment2.setCode("EQ-5678");
        equipment2.setName_equipment("Equipment 2");

        Location location1 = new Location();
        location1.setEnvironment(wrongEnvironment);
        equipment1.setLocation(location1);

        Location location2 = new Location();
        location2.setEnvironment(environment);
        equipment2.setLocation(location2);

        TrackingHistory tracking1 = new TrackingHistory();
        tracking1.setEquipment(equipment1);
        tracking1.setEnvironment(environment);
        tracking1.setAction(EnumTrackingAction.ENTER);

        TrackingHistory tracking2 = new TrackingHistory();
        tracking2.setEquipment(equipment2);
        tracking2.setEnvironment(environment);
        tracking2.setAction(EnumTrackingAction.ENTER);

        List<TrackingHistory> trackingHistoryList = Arrays.asList(tracking1, tracking2);

        when(trackingHistoryRepository.findByEnvironment(environment)).thenReturn(trackingHistoryList);

        EquipmentResponsible responsible1 = new EquipmentResponsible();
        Responsible resp1 = new Responsible();
        resp1.setResponsible("John Doe");
        responsible1.setResponsible(resp1);
        responsible1.setEquipment(equipment1);

        List<EquipmentResponsible> responsibleList = Collections.singletonList(responsible1);
        when(equipmentResponsibleRepository.findByEquipment(equipment1)).thenReturn(responsibleList);

        List<TrackingHistoryWrongLocationDTO> result = trackingHistoryServiceImp.findWrongLocationEquipments(environmentId);

        // Verificações
        assertNotNull(result);
        assertEquals(1, result.size());  // Apenas o equipamento1 deve estar na lista
        TrackingHistoryWrongLocationDTO dto = result.get(0);
        assertEquals(equipment1.getCode(), dto.code());
        assertEquals(1, dto.responsibles().size());
        assertEquals("John Doe", dto.responsibles().get(0));

        // Verifica se os métodos foram chamados conforme esperado
        verify(environmentRepository).findById(environmentId);
        verify(trackingHistoryRepository).findByEnvironment(environment);
        verify(equipmentResponsibleRepository).findByEquipment(equipment1);
    }

    @Test
    void deleteTrackingById() {
        Long trackingId = 1L;

        TrackingHistory trackingHistory = new TrackingHistory();
        trackingHistory.setId(trackingId);
        when(trackingHistoryRepository.findById(trackingHistory.getId())).thenReturn(Optional.of(trackingHistory));

        trackingHistoryServiceImp.deleteTrackingById(trackingHistory.getId());

        verify(trackingHistoryRepository).deleteById(trackingId);
    }
}