package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Environment.EnvironmentCreateDTO;
import com.MapView.BackEnd.dtos.Environment.EnvironmentDetailsDTO;
import com.MapView.BackEnd.dtos.Environment.EnvironmentUpdateDTO;
import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import com.MapView.BackEnd.repository.EnvironmentRepository;
import com.MapView.BackEnd.repository.RaspberryRepository;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
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
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test") // configurar o banco de dados H2
@Transactional
public class EnvironmentServiceImpTest {
    // mock substituir a implementação real pela implementação fake

    @InjectMocks
    private EnvironmentServiceImp environmentServiceImp;

    @Mock
    private EnvironmentRepository environmentRepository;

    @Mock
    private RaspberryRepository raspberryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserLogRepository userLogRepository;


    @Test
    void testCreateEnvironment() {
        Long userLogId = 1L;
        String raspId = "RASP";
        Long environmentId = 1L;
        Long buildingId = 1L;
        Long areaId = 1L;

        Users users = new Users();
        users.setId_user(userLogId);
        users.setOperative(true);

        Building building = new Building();
        building.setId_building(buildingId);

        Area area = new Area();
        area.setId_area(areaId);

        Raspberry raspberry = new Raspberry();
        raspberry.setArea(area);
        raspberry.setBuilding(building);
        raspberry.setId_raspberry(raspId);

        // Configurando mocks
        when(raspberryRepository.findById(raspId)).thenReturn(Optional.of(raspberry));
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));

        EnvironmentCreateDTO createDTO = new EnvironmentCreateDTO("Sala 2", raspId);

        Environment environment = new Environment();
        environment.setId_environment(environmentId);
        environment.setEnvironment_name(createDTO.environment_name());
        environment.setRaspberry(raspberry);
        environment.setOperative(true);

        when(environmentRepository.save(any(Environment.class))).thenReturn(environment);

        EnvironmentDetailsDTO result = environmentServiceImp.createEnvironment(createDTO, userLogId);
        System.out.println(result);

        // Verificações
        assertNotNull(result);
        assertEquals("Sala 2", result.environment_name());
    }

    @Test
    void testGetEnvironment() {
        Long userLogId = 1L;
        String raspId = "RASP";
        Long environmentId = 1L;
        Long buildingId = 1L;
        Long areaId = 1L;

        Users users = new Users();
        users.setId_user(userLogId);
        users.setOperative(true);

        Building building = new Building();
        building.setId_building(buildingId);

        Area area = new Area();
        area.setId_area(areaId);

        Raspberry raspberry = new Raspberry();
        raspberry.setArea(area);
        raspberry.setBuilding(building);
        raspberry.setId_raspberry(raspId);

        // Configurando mocks
        when(raspberryRepository.findById(raspId)).thenReturn(Optional.of(raspberry));
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));

        Environment environment = new Environment();
        environment.setId_environment(environmentId);
        environment.setEnvironment_name("Sala 1");
        environment.setRaspberry(raspberry);
        environment.setOperative(true);

        when(environmentRepository.findById(environmentId)).thenReturn(Optional.of(environment));
        EnvironmentDetailsDTO result = environmentServiceImp.getEnvironment(environmentId, userLogId);

        assertNotNull(result);
        assertEquals(environmentId, result.id_environment());
        assertEquals("Sala 1", result.environment_name());
    }

    @Test
    void testGetAllEnvironment() {
        Long userLogId = 1L;
        String raspId = "RASP";
        Long environmentId = 1L;
        Long buildingId = 1L;
        Long areaId = 1L;

        Users users = new Users();
        users.setId_user(userLogId);
        users.setOperative(true);

        Building building = new Building();
        building.setId_building(buildingId);

        Area area = new Area();
        area.setId_area(areaId);

        Raspberry raspberry = new Raspberry();
        raspberry.setArea(area);
        raspberry.setBuilding(building);
        raspberry.setId_raspberry(raspId);

        // Configurando mocks
        when(raspberryRepository.findById(raspId)).thenReturn(Optional.of(raspberry));
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));

        Environment environment = new Environment();
        environment.setId_environment(environmentId);
        environment.setEnvironment_name("Sala 1");
        environment.setRaspberry(raspberry);
        environment.setOperative(true);

        when(environmentRepository.findEnvironmentByOperativeTrue()).thenReturn(Collections.singletonList(environment));
        List<EnvironmentDetailsDTO> result = environmentServiceImp.getAllEnvironment(userLogId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Sala 1", result.get(0).environment_name());
    }

    @Test
    void testUpdateEnvironment() {
        Long userLogId = 1L;
        String raspId = "RASP";
        Long environmentId = 1L;
        Long buildingId = 1L;
        Long areaId = 1L;

        Users users = new Users();
        users.setId_user(userLogId);
        users.setOperative(true);

        Building building = new Building();
        building.setId_building(buildingId);

        Area area = new Area();
        area.setId_area(areaId);

        Raspberry raspberry = new Raspberry();
        raspberry.setArea(area);
        raspberry.setBuilding(building);
        raspberry.setId_raspberry(raspId);
        raspberry.setOperative(true);

        // Configurando mocks
        when(raspberryRepository.findById(raspId)).thenReturn(Optional.of(raspberry));
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));

        Environment environment = new Environment();
        environment.setId_environment(environmentId);
        environment.setEnvironment_name("Sala 1");
        environment.setRaspberry(raspberry);
        environment.setOperative(true);

        when(environmentRepository.findById(environmentId)).thenReturn(Optional.of(environment));

        EnvironmentUpdateDTO updateDTO = new EnvironmentUpdateDTO("Sala nova", raspId);
        EnvironmentDetailsDTO result = environmentServiceImp.updateEnvironment(environmentId, updateDTO, userLogId);

        assertNotNull(result);
        assertEquals("Sala nova", result.environment_name());
    }

    @Test
    void testActivateEnvironment() {
        Long environmentId = 1L;
        Long userLogId = 1L;

        Users users = new Users();
        users.setId_user(userLogId);
        users.setOperative(true);

        // Mock do usuário
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));

        // Mock do ambiente que será ativado
        Environment environment = new Environment();
        environment.setId_environment(environmentId);
        environment.setOperative(false); // Ambiente inicialmente inativo

        when(environmentRepository.findById(environmentId)).thenReturn(Optional.of(environment));

        environmentServiceImp.activateEnvironment(environmentId, userLogId);

        // Verificações
        assertTrue(environment.isOperative());
    }

    @Test
    void testInactivateEnvironment() {
        Long environmentId = 1L;
        Long userLogId = 1L;

        Users users = new Users();
        users.setId_user(userLogId);
        users.setOperative(true);

        // Mock do usuário
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));

        // Mock do ambiente que será inativado
        Environment environment = new Environment();
        environment.setId_environment(environmentId);
        environment.setOperative(true); // Ambiente inicialmente ativo

        when(environmentRepository.findById(environmentId)).thenReturn(Optional.of(environment));

        environmentServiceImp.inactivateEnvironment(environmentId, userLogId);

        // Verificações
        assertFalse(environment.isOperative());
    }

    @Test
    void testCreateEnvironment_UserNotFound() {
        Long userLogId = 1L;
        String raspId = "RASP";

        // Mock do retorno vazio ao procurar o usuário
        when(userRepository.findById(userLogId)).thenReturn(Optional.empty());

        EnvironmentCreateDTO createDTO = new EnvironmentCreateDTO("Sala 2", raspId);

        // Executar a chamada e esperar uma exceção
        assertThrows(NotFoundException.class, () -> {
            environmentServiceImp.createEnvironment(createDTO, userLogId);
        });
    }

    @Test
    void testGetEnvironment_NotFound() {
        Long userLogId = 1L;
        Long environmentId = 1L;

        // Mock do retorno vazio ao procurar o ambiente
        when(environmentRepository.findById(environmentId)).thenReturn(Optional.empty());

        // Executar a chamada e esperar uma exceção
        assertThrows(NotFoundException.class, () -> {
            environmentServiceImp.getEnvironment(environmentId, userLogId);
        });
    }


}
