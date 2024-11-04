package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.CostCenter.CostCenterCreateDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterDetailsDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterUpdateDTO;
import com.MapView.BackEnd.entities.CostCenter;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.repository.CostCenterRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
@ActiveProfiles("test") // configurar o banco de dados H2
@Transactional
public class CostCenterServiceImpTest {

    @InjectMocks
    private CostCenterServiceImp costCenterServiceImp;

    @Mock
    private CostCenterRepository costCenterRepository;

    @Mock
    private UserLogRepository userLogRepository; // Mock da interface de repositório para logs de usuário

    @Mock
    private UserRepository userRepository;

    @Test
    @Transactional
    void testCreateCostCenterSuccess(){
        Long userLogId = 1L;
        Users user = new Users();

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // DTO para a criação do centro de custo
        CostCenterCreateDTO createDTO = new CostCenterCreateDTO("40402");

        // Centro de custo esperado após ser salvo
        CostCenter savedCostCenter = new CostCenter(createDTO);
        savedCostCenter.setId_cost_center(1L);
        savedCostCenter.setOperative(true);

        // Mock do repositório para salvar o centro de custo
        when(costCenterRepository.save(any(CostCenter.class))).thenReturn(savedCostCenter);

        // Chamada ao método de criação
        CostCenterDetailsDTO result = costCenterServiceImp.createCostCenter(createDTO, userLogId);

        // Verificações
        assertNotNull(result);
        assertEquals(createDTO.costCenter(), result.costCenter());

        // Verificar que o log foi registrado
        verify(userLogRepository, times(1)).save(any(UserLog.class));
    }

    @Test
    @Transactional
    void testGetCostCenterSuccess(){
        Long userLogId = 1L;
        Long costCenterId = 1L;

        Users user = new Users();
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // DTO para a criação do centro de custo
        CostCenterCreateDTO createDTO = new CostCenterCreateDTO("40402");

        // Centro de custo esperado após ser salvo
        CostCenter costCenter = new CostCenter(createDTO);
        costCenter.setId_cost_center(costCenterId);
        costCenter.setOperative(true);

        when(costCenterRepository.findById(costCenterId)).thenReturn(Optional.of(costCenter));

        CostCenterDetailsDTO result = costCenterServiceImp.getCostCenter(costCenterId, userLogId);

        assertNotNull(result);
        assertEquals("40402", result.costCenter());
    }

    @Test
    @Transactional
    void testGetAllCostCentSuccess(){
        Long userLogId = 1L;
        Long costCenterId = 1L;

        Users user = new Users();
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // DTO para a criação do centro de custo
        CostCenterCreateDTO createDTO = new CostCenterCreateDTO("40402");

        // Centro de custo esperado após ser salvo
        CostCenter costCenter = new CostCenter(createDTO);
        costCenter.setId_cost_center(costCenterId);
        costCenter.setOperative(true);

        when(costCenterRepository.findAllByOperativeTrue()).thenReturn(Collections.singletonList(costCenter));
        List<CostCenterDetailsDTO> result = costCenterServiceImp.getAllCostCenter(userLogId);
        System.out.println(result);

        assertNotNull(result);
        assertFalse(result.isEmpty(), "A lista não deve estar vazia");
        assertEquals(1, result.size());
        assertEquals("40402", result.get(0).costCenter());
    }

    @Test
    @Transactional
    void testUpdateCostCentSuccess(){
        Long userLogId = 1L;
        Long costCenterId = 1L;

        CostCenterUpdateDTO updateDTO = new CostCenterUpdateDTO("65892");

        Users user = new Users();
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));


        // Centro de custo esperado após ser salvo
        CostCenter costCenter = new CostCenter();
        costCenter.setId_cost_center(costCenterId);
        costCenter.setCostCenter("40402");
        costCenter.setOperative(true);

        when(costCenterRepository.findById(costCenterId)).thenReturn(Optional.of(costCenter));

        CostCenterDetailsDTO result = costCenterServiceImp.updateCostCenter(costCenterId, updateDTO, userLogId);

        assertNotNull(result);
        assertEquals("65892", result.costCenter());
    }

    @Test
    @Transactional
    void testActivateCostCenterSuccess() {
        Long userLogId = 1L;
        Long costCenterId = 1L;

        Users user = new Users();
        CostCenter costCenter = new CostCenter();
        costCenter.setId_cost_center(costCenterId);
        costCenter.setOperative(false); // Começa como inativo

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));
        when(costCenterRepository.findById(costCenterId)).thenReturn(Optional.of(costCenter));
        when(costCenterRepository.save(any(CostCenter.class))).thenReturn(costCenter);

        costCenterServiceImp.activateCostCenter(costCenterId, userLogId);

        assertTrue(costCenter.isOperative());
        verify(userLogRepository, times(1)).save(any(UserLog.class));
    }

    @Test
    @Transactional
    void testInactivateCostCenterSuccess() {
        Long userLogId = 1L;
        Long costCenterId = 1L;

        Users user = new Users();
        CostCenter costCenter = new CostCenter();
        costCenter.setId_cost_center(costCenterId);
        costCenter.setOperative(true); // Começa como ativo

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));
        when(costCenterRepository.findById(costCenterId)).thenReturn(Optional.of(costCenter));
        when(costCenterRepository.save(any(CostCenter.class))).thenReturn(costCenter);

        costCenterServiceImp.inactivateCostCenter(costCenterId, userLogId);

        assertFalse(costCenter.isOperative());
        verify(userLogRepository, times(1)).save(any(UserLog.class));
    }

    @Test
    @Transactional
    void testCreateCostCenterUserNotFound() {
        Long userLogId = 1L;

        // DTO para a criação do centro de custo
        CostCenterCreateDTO createDTO = new CostCenterCreateDTO("40402");

        // Simula usuário não encontrado
        when(userRepository.findById(userLogId)).thenReturn(Optional.empty());

        // Executa e verifica exceção
        Exception exception = assertThrows(RuntimeException.class, () -> {
            costCenterServiceImp.createCostCenter(createDTO, userLogId);
        });

        assertEquals("Id not found", exception.getMessage());
    }

    @Test
    @Transactional
    void testGetCostCenterNotFound() {
        Long userLogId = 1L;
        Long costCenterId = 1L;

        Users user = new Users();
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Simula centro de custo não encontrado
        when(costCenterRepository.findById(costCenterId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            costCenterServiceImp.getCostCenter(costCenterId, userLogId);
        });

        assertEquals("Id not found", exception.getMessage());
    }

}
