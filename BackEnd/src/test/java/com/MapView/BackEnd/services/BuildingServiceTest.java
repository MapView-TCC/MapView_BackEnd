package com.MapView.BackEnd.services;

import com.MapView.BackEnd.dtos.Building.BuildingCreateDTO;
import com.MapView.BackEnd.dtos.Building.BuildingDetailsDTO;
import com.MapView.BackEnd.dtos.Building.BuildingUpdateDTO;
import com.MapView.BackEnd.entities.Building;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.repository.BuildingRepository;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.serviceImp.BuildingServiceImp;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test") // configurar o banco de dados H2
@Transactional
public class BuildingServiceTest {

    @InjectMocks
    private BuildingServiceImp buildingServiceImp;

    @Mock
    private BuildingRepository buildingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserLogRepository userLogRepository;

    @Mock
    private Users users;

    @Test
    void TestGetBuilding(){
        // Definir IDs de teste
        Long userLogId = 1L;
        Long buildingId = 1L;

        Users user = new Users();
        user.setId_user(userLogId);// Definindo ID do usuário mockado

        // Configurando comportamento esperado do repositório de usuários
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Mock da entidade Building
        Building building = new Building();
        building.setId_building(buildingId);
        building.setBuilding_code("ETS");
        building.setOperative(true);

        // Supondo que exista um serviço para buscar uma classe, pode-se chamá-lo e verificar o comportamento
        when(buildingRepository.findById(buildingId)).thenReturn(Optional.of(building));

        // Chamada do método para testar
        BuildingDetailsDTO result = buildingServiceImp.getBuilding(buildingId, userLogId);

        System.out.println(result);

        // Verificações
        assertNotNull(result);
        assertEquals(buildingId, result.id_building());
        assertEquals("ETS", result.building_code());
    }

    @Test
    void TestGetAllBuilding() {
        // Definir IDs de teste
        Long userLogId = 1L;
        Long buildingId = 3L;
        Long buildingId1 = 4L;

        // Mock do usuário
        Users user = new Users();
        user.setId_user(userLogId);

        // Configurando comportamento esperado do repositório de usuários
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Criando dois prédios para o teste
        Building building = new Building();
        building.setId_building(buildingId);
        building.setBuilding_code("TESTE LEGAL!");
        building.setOperative(true);

        Building building1 = new Building();
        building1.setId_building(buildingId1);
        building1.setBuilding_code("TESTE");
        building1.setOperative(true);

        // Retornar uma lista com os dois prédios
        when(buildingRepository.findAllByOperativeTrue()).thenReturn(Arrays.asList(building, building1));

        // Executar o método a ser testado
        List<BuildingDetailsDTO> result = buildingServiceImp.getAllBuilding(userLogId);

        // Imprimir o resultado para visualização
        System.out.println(result);

        // Verificações
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("TESTE LEGAL!", result.get(0).building_code());
        assertEquals("TESTE", result.get(1).building_code());
    }

    @Test
    void TestCreateBuilding(){
        // Definir IDs de teste
        Long userLogId = 1L;
        Long buildingId = 1L;

        // Mock do usuario
        Users user = new Users();
        user.setId_user(userLogId);

        // Simulando a criação no DTO
        BuildingCreateDTO createDTO = new BuildingCreateDTO("BTC");

        // serviço de criação do building sendo chamado
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        Building building = new Building();
        building.setId_building(buildingId);
        building.setBuilding_code(createDTO.building_code());
        building.setOperative(true);

        when(buildingRepository.save(any(Building.class))).thenReturn(building);

        BuildingDetailsDTO result = buildingServiceImp.createBuilding(createDTO, userLogId);
        System.out.println(result);

        assertNotNull(result);
        assertEquals(buildingId, result.id_building());
    }

    @Test
    void TestUpdateBuilding(){
        // Definir IDs de teste
        Long userLogId = 1L;
        Long buildingId = 1L;

        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        Building building = new Building();
        building.setId_building(buildingId);
        building.setBuilding_code("Original");
        building.setOperative(true);

        when(buildingRepository.findById(buildingId)).thenReturn(Optional.of(building));

        BuildingUpdateDTO updateDTO = new BuildingUpdateDTO("Copia");
        BuildingDetailsDTO result = buildingServiceImp.updateBuilding(buildingId, updateDTO, userLogId);

        assertNotNull(result);
        assertEquals("Copia", result.building_code());
        verify(buildingRepository).save(building);
    }

    @Test
    void testActiveBuilding() {
        Long buildingId = 1L;
        Long userLogId = 1L;

        // Mock do usuário, agora operativo
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Mock da classe, inicialmente inativa
        Building building = new Building();
        building.setId_building(buildingId);
        building.setOperative(false); // Deve ser inativa para ser ativada

        when(buildingRepository.findById(buildingId)).thenReturn(Optional.of(building));

        // Chama o método para ativar a classe
        buildingServiceImp.activateBuilding(buildingId, userLogId);

        // Verifica se a classe agora está activa
        assertTrue(building.isOperative());
        // Verifica se o método save foi chamado no repositório de logs de usuário
        verify(userLogRepository).save(any(UserLog.class));
    }

    @Test
    void TestInactiveBuilding(){
        Long buildingId = 1L;
        Long userLogId = 1L;

        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        Building building = new Building();
        building.setId_building(buildingId);
        building.setOperative(true);

        when(buildingRepository.findById(buildingId)).thenReturn(Optional.of(building));

        buildingServiceImp.inactivateBuilding(buildingId, userLogId);

        assertFalse(building.isOperative());
        verify(userLogRepository).save(any(UserLog.class));
    }
}
