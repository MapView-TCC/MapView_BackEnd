package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.FileStorageProperties;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Path;
import java.nio.file.Paths;
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
    private UserLogRepository userLogRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void getEquipmentCode() {
        //equipmentServiceImp = new EquipmentServiceImp(equipmentRepository, userLogRepository, userRepository);

        long rfid = 132456789;
        Long userLogId = 1L;

        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        // Criação dos objetos necessários para o teste
        Equipment equipment = new Equipment("EQ-123", rfid);

        // Configuração dos mocks
        when(equipmentRepository.findByCode("EQ-123")).thenReturn(Optional.of(equipment));
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Execução do método que está sendo testado
        EquipmentDetailsDTO result = equipmentServiceImp.getEquipmentCode("EQ-123", userLogId);

        // Verificações
        assertNotNull(result);
        assertEquals("EQ-123", result.code());

        // Verifica se o UserLog foi salvo
        verify(userLogRepository).save(any(UserLog.class));
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
