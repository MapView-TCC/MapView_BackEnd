package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.MainOwner.MainOwnerCreateDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerDetailsDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerUpdateDTO;
import com.MapView.BackEnd.entities.CostCenter;
import com.MapView.BackEnd.entities.MainOwner;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.repository.CostCenterRepository;
import com.MapView.BackEnd.repository.MainOwnerRepository;
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

@SpringBootTest
@ActiveProfiles("test") // configurar o banco de dados H2
@Transactional
public class MainOwnerServiceImpTest {

    @InjectMocks
    private MainOwnerServiceImp mainOwnerServiceImp;

    @Mock
    private MainOwnerRepository mainOwnerRepository;

    @Mock
    private CostCenterRepository costCenterRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserLogRepository userLogRepository;

    @Test
    void testGetMainOwner() {
        Long userLogId = 1L;
        Long costCenterId = 1L;
        Long mainOwnerId = 1L;

        Users users = new Users();
        users.setId_user(userLogId);
        users.setOperative(true);

        CostCenter costCenter = new CostCenter();
        costCenter.setId_cost_center(costCenterId);
        costCenter.setCostCenter("40402");
        costCenter.setOperative(true);

        MainOwner mainOwner = new MainOwner();
        mainOwner.setId_owner(mainOwnerId);
        mainOwner.setCodOwner("FMA6CA");
        mainOwner.setCostCenter(costCenter);
        mainOwner.setOperative(true);

        when(costCenterRepository.findById(costCenterId)).thenReturn(Optional.of(costCenter));
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));
        when(mainOwnerRepository.findById(mainOwnerId)).thenReturn(Optional.of(mainOwner));

        MainOwnerDetailsDTO result = mainOwnerServiceImp.getMainOwner(mainOwnerId, userLogId);

        assertNotNull(result);
        assertEquals(mainOwnerId, result.id_owner());
        assertEquals("FMA6CA", mainOwner.getCodOwner());
        assertEquals("40402", mainOwner.getCostCenter().getCostCenter());
    }

    @Test
    void testGetAllMainOwner() {
        Long userLogId = 1L;
        Long costCenterId = 1L;
        Long mainOwnerId = 1L;

        Users users = new Users();
        users.setId_user(userLogId);
        users.setOperative(true);

        CostCenter costCenter = new CostCenter();
        costCenter.setId_cost_center(costCenterId);
        costCenter.setCostCenter("40402");
        costCenter.setOperative(true);

        MainOwner mainOwner = new MainOwner();
        mainOwner.setId_owner(mainOwnerId);
        mainOwner.setCodOwner("FMA6CA");
        mainOwner.setCostCenter(costCenter);
        mainOwner.setOperative(true);

        when(costCenterRepository.findById(costCenterId)).thenReturn(Optional.of(costCenter));
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));
        when(mainOwnerRepository.findAllByOperativeTrue()).thenReturn(Collections.singletonList(mainOwner));

        List<MainOwnerDetailsDTO> result = mainOwnerServiceImp.getAllMainOwner(userLogId);
        System.out.println(result);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("FMA6CA", result.get(0).cod_owner());
    }

    @Test
    void testCreateMainOwner() {
        Long userLogId = 1L;
        Long costCenterId = 1L;
        Long mainOwnerId = 1L;

        Users users = new Users();
        users.setId_user(userLogId);
        users.setOperative(true);

        CostCenter costCenter = new CostCenter();
        costCenter.setId_cost_center(costCenterId);
        costCenter.setCostCenter("40402");
        costCenter.setOperative(true);

        MainOwnerCreateDTO createDTO = new MainOwnerCreateDTO("FMA6CA", costCenterId);

        MainOwner mainOwner = new MainOwner();
        mainOwner.setId_owner(mainOwnerId);
        mainOwner.setCodOwner(createDTO.cod_owner());
        mainOwner.setCostCenter(costCenter);
        mainOwner.setOperative(true);

        when(costCenterRepository.findById(costCenterId)).thenReturn(Optional.of(costCenter));
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));
        when(mainOwnerRepository.save(any(MainOwner.class))).thenReturn(mainOwner);

        MainOwnerDetailsDTO result = mainOwnerServiceImp.createMainOwner(createDTO, userLogId);
        System.out.println(result);

        assertNotNull(result);
        assertEquals(mainOwnerId, result.id_owner());
        assertEquals("FMA6CA", result.cod_owner());
        assertEquals("40402", result.costCenter().getCostCenter());
    }

    @Test
    void testUpdateMainOwner() {
        Long userLogId = 1L;
        Long costCenterId = 1L;
        Long mainOwnerId = 1L;
        Long costCenterId1 = 2L;

        Users users = new Users();
        users.setId_user(userLogId);
        users.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));

        CostCenter costCenter = new CostCenter();
        costCenter.setId_cost_center(costCenterId);
        costCenter.setCostCenter("40402");
        costCenter.setOperative(true);
        when(costCenterRepository.findById(costCenterId)).thenReturn(Optional.of(costCenter));

        CostCenter updateCostCenter = new CostCenter();
        costCenter.setId_cost_center(costCenterId1);
        costCenter.setCostCenter("30304");
        costCenter.setOperative(true);
        when(costCenterRepository.findById(costCenterId1)).thenReturn(Optional.of(updateCostCenter));

        MainOwner mainOwner = new MainOwner();
        mainOwner.setId_owner(mainOwnerId);
        mainOwner.setCodOwner("FMA6CA");
        mainOwner.setCostCenter(costCenter);
        mainOwner.setOperative(true);
        when(mainOwnerRepository.findById(mainOwnerId)).thenReturn(Optional.of(mainOwner));
        System.out.println("Before update" + mainOwner);

        MainOwnerUpdateDTO updateDTO = new MainOwnerUpdateDTO("AS65M8", costCenterId1);

        MainOwnerDetailsDTO result = mainOwnerServiceImp.updateMainOwner(mainOwnerId, updateDTO, userLogId);
        System.out.println("After update" + result);

        assertNotNull(result);
        assertEquals(mainOwnerId, result.id_owner());
        assertEquals(updateDTO.cod_owner(), result.cod_owner());
    }

    @Test
    void testActivateMainOwner() {
        Long userLogId = 1L;
        Long mainOwnerId = 1L;

        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        MainOwner mainOwner = new MainOwner();
        mainOwner.setId_owner(mainOwnerId);
        mainOwner.setCodOwner("FMA6CA");
        mainOwner.setOperative(false); // Inativo inicialmente

        // Configurando os mocks
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));
        when(mainOwnerRepository.findById(mainOwnerId)).thenReturn(Optional.of(mainOwner));

        // Chamando o método a ser testado
        mainOwnerServiceImp.activateMainOwner(mainOwnerId, userLogId);

        // Verificações
        assertTrue(mainOwner.isOperative(), "Main Owner should be activated");
        verify(mainOwnerRepository).save(mainOwner); // Verifica se o repositório foi chamado para salvar
        verify(userLogRepository).save(any(UserLog.class)); // Verifica se um UserLog foi salvo
    }

    @Test
    void testInactivateMainOwner() {
        Long userLogId = 1L;
        Long mainOwnerId = 1L;

        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        MainOwner mainOwner = new MainOwner();
        mainOwner.setId_owner(mainOwnerId);
        mainOwner.setCodOwner("FMA6CA");
        mainOwner.setOperative(true); // Ativo inicialmente

        // Configurando os mocks
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));
        when(mainOwnerRepository.findById(mainOwnerId)).thenReturn(Optional.of(mainOwner));

        // Chamando o método a ser testado
        mainOwnerServiceImp.inactivateMainOwner(mainOwnerId, userLogId);

        // Verificações
        assertFalse(mainOwner.isOperative(), "Main Owner should be inactivated");
        verify(mainOwnerRepository).save(mainOwner); // Verifica se o repositório foi chamado para salvar
        verify(userLogRepository).save(any(UserLog.class)); // Verifica se um UserLog foi salvo
    }

}
