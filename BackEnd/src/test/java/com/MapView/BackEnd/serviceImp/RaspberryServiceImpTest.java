package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Area.AreaDetailsDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryCreateDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryDetailsDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryUpdateDTO;
import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import com.MapView.BackEnd.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test") // configurar o banco de dados H2
@Transactional
class RaspberryServiceImpTest {

    @InjectMocks
    private RaspberryServiceImp raspberryServiceImp;
    @Mock
    private RaspberryRepository raspberryRepository;
    @Mock
    private AreaRepository areaRepository;
    @Mock
    private BuildingRepository buildingRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserLogRepository userLogRepository;

    @Test
    void testGetRaspberry() {
        Long userLogId = 1L;
        Long areaId = 1L;
        Long buildingId = 1L;
        String raspId = "raspId";

        Users users = new Users();
        users.setId_user(userLogId);
        users.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));

        // Mock da entidade Building
        Building building = new Building();
        building.setId_building(buildingId);
        building.setBuilding_code("ETS");
        building.setOperative(true);
        when(buildingRepository.findById(buildingId)).thenReturn(Optional.of(building));

        // Mock da área
        Area area = new Area();
        area.setId_area(areaId);
        area.setCode("Teste codigo");
        area.setArea_name("Teste nome");
        area.setOperative(true);
        when(areaRepository.findById(areaId)).thenReturn(Optional.of(area));

        Raspberry raspberry = new Raspberry();
        raspberry.setId_raspberry(raspId);
        raspberry.setBuilding(building);
        raspberry.setArea(area);
        raspberry.setOperative(true);
        when(raspberryRepository.findById(raspId)).thenReturn(Optional.of(raspberry));

        RaspberryDetailsDTO result = raspberryServiceImp.getRaspberry(raspberry.getId_raspberry(), userLogId);

        assertNotNull(result);
        assertEquals(raspId, result.id_raspberry());
        assertEquals("ETS", result.building().getBuilding_code());
        assertEquals("Teste nome", result.area().getArea_name());
    }

    @Test
    void testGetRaspberry_UserNotFound() {
        Long userLogId = 1L;
        String raspId = "raspId";

        // Simula o caso em que o usuário não é encontrado
        when(userRepository.findById(userLogId)).thenReturn(Optional.empty());

        // Verifica se uma NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> {
            raspberryServiceImp.getRaspberry(raspId, userLogId);
        });
    }

    @Test
    void testGetAllRaspberry() {
        Long userLogId = 1L;
        Long areaId = 1L;
        Long buildingId = 1L;
        String raspId = "raspId";

        Users users = new Users();
        users.setId_user(userLogId);
        users.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));

        // Mock da entidade Building
        Building building = new Building();
        building.setId_building(buildingId);
        building.setBuilding_code("ETS");
        building.setOperative(true);
        when(buildingRepository.findById(buildingId)).thenReturn(Optional.of(building));

        // Mock da área
        Area area = new Area();
        area.setId_area(areaId);
        area.setCode("Teste codigo");
        area.setArea_name("Teste nome");
        area.setOperative(true);
        when(areaRepository.findById(areaId)).thenReturn(Optional.of(area));

        Raspberry raspberry = new Raspberry();
        raspberry.setId_raspberry(raspId);
        raspberry.setBuilding(building);
        raspberry.setArea(area);
        raspberry.setOperative(true);
        when(raspberryRepository.findAllByOperativeTrue()).thenReturn(Collections.singletonList(raspberry));

        List<RaspberryDetailsDTO> result = raspberryServiceImp.getAllRaspberry(userLogId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("ETS", result.get(0).building().getBuilding_code());
        assertEquals("Teste nome", result.get(0).area().getArea_name());
    }

    @Test
    void testCreateRaspberry() {
        Long userLogId = 1L;
        Long areaId = 1L;
        Long buildingId = 1L;
        String raspId = "raspId";

        Users users = new Users();
        users.setId_user(userLogId);
        users.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));

        // Mock da entidade Building
        Building building = new Building();
        building.setId_building(buildingId);
        building.setBuilding_code("ETS");
        building.setOperative(true);
        when(buildingRepository.findById(buildingId)).thenReturn(Optional.of(building));

        // Mock da área
        Area area = new Area();
        area.setId_area(areaId);
        area.setCode("Teste codigo");
        area.setArea_name("Teste nome");
        area.setOperative(true);
        when(areaRepository.findById(areaId)).thenReturn(Optional.of(area));

        RaspberryCreateDTO createDTO = new RaspberryCreateDTO(raspId, buildingId, areaId);

        Raspberry raspberry = new Raspberry();
        raspberry.setId_raspberry(createDTO.id_raspberry());
        raspberry.setBuilding(building);
        raspberry.setArea(area);
        raspberry.setOperative(true);
        when(raspberryRepository.save(any(Raspberry.class))).thenReturn(raspberry);

        RaspberryDetailsDTO result = raspberryServiceImp.createRaspberry(createDTO, userLogId);

        assertNotNull(result);
        assertEquals(raspId, result.id_raspberry());
        assertEquals("ETS", result.building().getBuilding_code());
        assertEquals("Teste nome", result.area().getArea_name());
    }

    @Test
    void testUpdateRaspberry() {
        Long userLogId = 1L;
        Long areaId = 1L;
        Long buildingId = 1L;
        Long areaId1 = 2L;
        Long buildingId1 = 2L;
        String raspId = "raspId";

        Users users = new Users();
        users.setId_user(userLogId);
        users.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));

        // Mock da entidade Building
        Building building = new Building();
        building.setId_building(buildingId);
        building.setBuilding_code("ETS");
        building.setOperative(true);
        when(buildingRepository.findById(buildingId)).thenReturn(Optional.of(building));

        // Mock da área
        Area area = new Area();
        area.setId_area(areaId);
        area.setCode("Teste codigo");
        area.setArea_name("Teste nome");
        area.setOperative(true);
        when(areaRepository.findById(areaId)).thenReturn(Optional.of(area));

        // Mock da entidade Building para atualização
        Building updateBuilding = new Building();
        updateBuilding.setId_building(buildingId1);
        updateBuilding.setBuilding_code("ETS update");
        updateBuilding.setOperative(true);
        when(buildingRepository.findById(buildingId1)).thenReturn(Optional.of(updateBuilding));

        // Mock da área para atualização
        Area updateArea = new Area();
        updateArea.setId_area(areaId1);
        updateArea.setCode("Teste update");
        updateArea.setArea_name("Teste update");
        updateArea.setOperative(true);
        when(areaRepository.findById(areaId1)).thenReturn(Optional.of(updateArea));

        Raspberry raspberry = new Raspberry();
        raspberry.setId_raspberry(raspId);
        raspberry.setBuilding(building);
        raspberry.setArea(area);
        raspberry.setOperative(true);
        when(raspberryRepository.findById(raspId)).thenReturn(Optional.of(raspberry));

        // Criação do DTO de atualização
        RaspberryUpdateDTO updateData = new RaspberryUpdateDTO("New Raspberry Name", areaId1, buildingId1);

        // Executa o método a ser testado
        RaspberryDetailsDTO result = raspberryServiceImp.updateRaspberry(raspId, updateData, userLogId);

        // Verificações
        assertNotNull(result);
        assertEquals("New Raspberry Name", result.id_raspberry());
        assertEquals("ETS update", result.building().getBuilding_code());
        assertEquals("Teste update", result.area().getArea_name());

        // Verifica se o UserLog foi salvo corretamente
        verify(userLogRepository).save(any(UserLog.class));
    }

    @Test
    void testActiveRaspberry() {
        Long userLogId = 1L;
        String raspId = "raspId";

        // Mock da entidade Users
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Mock da entidade Raspberry inativa
        Raspberry raspberry = new Raspberry();
        raspberry.setId_raspberry(raspId);
        raspberry.setOperative(false);
        when(raspberryRepository.findById(raspId)).thenReturn(Optional.of(raspberry));

        // Executa o método a ser testado
        raspberryServiceImp.activeRaspberry(raspId, userLogId);

        // Verificações
        assertTrue(raspberry.isOperative(), "Raspberry should be active after method call");
        verify(userLogRepository).save(any(UserLog.class));
    }

    @Test
    void testInactivateRaspberry() {
        Long userLogId = 1L;
        String raspId = "raspId";

        // Mock da entidade Users
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Mock da entidade Raspberry ativa
        Raspberry raspberry = new Raspberry();
        raspberry.setId_raspberry(raspId);
        raspberry.setOperative(true);
        when(raspberryRepository.findById(raspId)).thenReturn(Optional.of(raspberry));

        // Executa o método a ser testado
        raspberryServiceImp.inactivateRaspberry(raspId, userLogId);

        // Verificações
        assertFalse(raspberry.isOperative(), "Raspberry should be inactive after method call");
        verify(userLogRepository).save(any(UserLog.class));
    }

}