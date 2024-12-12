package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleDetailsDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleUpdateDTO;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.EquipmentResponsible;
import com.MapView.BackEnd.entities.Responsible;
import com.MapView.BackEnd.repository.EquipmentRepository;
import com.MapView.BackEnd.repository.EquipmentResponsibleRepository;
import com.MapView.BackEnd.repository.ResponsibleRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test") // configurar o banco de dados H2
@Transactional
// MockitoAnnotations.openMocks(this); -> se colocar isso aqui dentro do metodo não precisa dessas configurações acima
class EquipmentResponsibleServiceImpTest {

    @InjectMocks
    private EquipmentResponsibleServiceImp equipmentResponsibleServiceImp;

    @Mock
    private EquipmentResponsibleRepository equipmentResponsibleRepository;

    @Mock
    private EquipmentRepository equipmentRepository;

    @Mock
    private  ResponsibleRepository responsibleRepository;

    @Test
    void getEquipmentResponsible() {

        Long responsibleId = 1L;
        Long equipmentResponsibleId = 1L;
        Long equipmentId = 1L;

        Equipment equipment = new Equipment();
        equipment.setId_equipment(equipmentId);
        equipment.setCode("E001");
        equipment.setName_equipment("Equipamento Teste");
        equipment.setType("Tipo novo");
        when(equipmentRepository.findByCode(equipment.getCode())).thenReturn(Optional.of(equipment));

        Responsible responsible = new Responsible();
        responsible.setId_responsible(responsibleId);
        responsible.setResponsible("Maria");
        responsible.setEdv("92903520");
        responsible.setOperative(true);
        when(responsibleRepository.findById(responsibleId)).thenReturn(Optional.of(responsible));

        EquipmentResponsible equipmentResponsible = new EquipmentResponsible();
        equipmentResponsible.setId_equip_resp(equipmentResponsibleId);
        equipmentResponsible.setEquipment(equipment);
        equipmentResponsible.setResponsible(responsible);
        equipmentResponsible.setStart_usage(LocalDate.now());
        equipmentResponsible.setEnd_usage(null);
        when(equipmentResponsibleRepository.findById(equipmentResponsibleId)).thenReturn(Optional.of(equipmentResponsible));

        EquipmentResponsibleDetailsDTO result = equipmentResponsibleServiceImp.getEquipmentResponsible(equipmentResponsible.getId_equip_resp());
        System.out.println(result);

        // Verificações
        assertNotNull(result);
        assertEquals(equipmentResponsibleId, result.id_equip_resp(), "O ID do responsável pelo equipamento deve ser igual ao esperado");
        assertEquals(equipment.getCode(), result.equipment().getCode(), "O código do equipamento deve corresponder ao esperado");
        assertEquals(responsible.getResponsible(), result.responsible().getResponsible(), "O nome do responsável deve corresponder ao esperado");
    }

    @Test
    void getAllEquipmentResponsible() {

        Long responsibleId = 1L;
        Long equipmentResponsibleId = 1L;
        Long equipmentId = 1L;

        Equipment equipment = new Equipment();
        equipment.setId_equipment(equipmentId);
        equipment.setCode("E001");
        equipment.setName_equipment("Equipamento Teste");
        equipment.setType("Tipo novo");
        when(equipmentRepository.findByCode(equipment.getCode())).thenReturn(Optional.of(equipment));

        Responsible responsible = new Responsible();
        responsible.setId_responsible(responsibleId);
        responsible.setResponsible("Maria");
        responsible.setEdv("92903520");
        responsible.setOperative(true);
        when(responsibleRepository.findById(responsibleId)).thenReturn(Optional.of(responsible));

        EquipmentResponsible equipmentResponsible = new EquipmentResponsible();
        equipmentResponsible.setId_equip_resp(equipmentResponsibleId);
        equipmentResponsible.setEquipment(equipment);
        equipmentResponsible.setResponsible(responsible);
        equipmentResponsible.setStart_usage(LocalDate.now());
        equipmentResponsible.setEnd_usage(null);
        when(equipmentResponsibleRepository.findAllByOperativeTrue()).thenReturn(Collections.singletonList(equipmentResponsible));

        List<EquipmentResponsibleDetailsDTO> result = equipmentResponsibleServiceImp.getAllEquipmentResponsible();
        System.out.println(result);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(equipmentResponsibleId, result.get(0).id_equip_resp(), "O ID do responsável pelo equipamento deve ser igual ao esperado");
        assertEquals(equipment.getCode(), result.get(0).equipment().getCode(), "O código do equipamento deve corresponder ao esperado");
        assertEquals(responsible.getResponsible(), result.get(0).responsible().getResponsible(), "O nome do responsável deve corresponder ao esperado");
    }

    @Test
    void createEquipmentResponsible() {

        Long responsibleId = 1L;
        Long equipmentResponsibleId = 1L;
        Long equipmentId = 1L;

        Equipment equipment = new Equipment();
        equipment.setId_equipment(equipmentId);
        equipment.setCode("E001");
        equipment.setName_equipment("Equipamento Teste");
        equipment.setType("Tipo novo");
        when(equipmentRepository.findByCode(equipment.getCode())).thenReturn(Optional.of(equipment));

        Responsible responsible = new Responsible();
        responsible.setId_responsible(responsibleId);
        responsible.setResponsible("Maria");
        responsible.setEdv("92903520");
        responsible.setOperative(true);
        when(responsibleRepository.findById(responsibleId)).thenReturn(Optional.of(responsible));

        EquipmentResponsible equipmentResponsible = new EquipmentResponsible();
        equipmentResponsible.setId_equip_resp(equipmentResponsibleId);
        equipmentResponsible.setEquipment(equipment);
        equipmentResponsible.setResponsible(responsible);
        equipmentResponsible.setStart_usage(LocalDate.now());
        equipmentResponsible.setEnd_usage(null);
        when(equipmentResponsibleRepository.findById(equipmentResponsibleId)).thenReturn(Optional.of(equipmentResponsible));
    }

    @Test
    void updateEquipmentResponsible() {

        Long responsibleId = 1L;
        Long equipmentResponsibleId = 1L;
        Long equipmentId = 1L;

        // Mock do equipamento
        Equipment equipment = new Equipment();
        equipment.setId_equipment(equipmentId);
        equipment.setCode("E001");
        equipment.setName_equipment("Equipamento Teste");
        equipment.setType("Tipo novo");
        equipment.setOperative(true);

        when(equipmentRepository.findById(equipmentId)).thenReturn(Optional.of(equipment));
        when(equipmentRepository.findByCode(equipment.getCode())).thenReturn(Optional.of(equipment));

        Responsible responsible = new Responsible();
        responsible.setId_responsible(responsibleId);
        responsible.setResponsible("Maria");
        responsible.setEdv("92903520");
        responsible.setOperative(true);
        when(responsibleRepository.findById(responsibleId)).thenReturn(Optional.of(responsible));

        EquipmentResponsible equipmentResponsible = new EquipmentResponsible();
        equipmentResponsible.setId_equip_resp(equipmentResponsibleId);
        equipmentResponsible.setEquipment(equipment);
        equipmentResponsible.setResponsible(responsible);
        equipmentResponsible.setStart_usage(LocalDate.now());
        equipmentResponsible.setEnd_usage(null);
        when(equipmentResponsibleRepository.findById(equipmentResponsibleId)).thenReturn(Optional.of(equipmentResponsible));

        // Criando o DTO de atualização
        EquipmentResponsibleUpdateDTO updateDTO = new EquipmentResponsibleUpdateDTO(equipmentId, responsibleId, LocalDate.now(), LocalDate.now().plusYears(2));

        EquipmentResponsibleDetailsDTO result = equipmentResponsibleServiceImp.updateEquipmentResponsible(equipmentResponsibleId, updateDTO);
        System.out.println(result);

        // Verificações
        assertNotNull(result);
        assertEquals(equipmentId, result.equipment().getId_equipment());
        assertEquals(responsibleId, result.responsible().getId_responsible());
        assertEquals(LocalDate.now(), result.start_usage());
        assertEquals(LocalDate.now().plusYears(2), result.end_usage());

        // Verificação se o método save foi chamado
        verify(equipmentResponsibleRepository).save(equipmentResponsible);
    }

    @Test
    void activateEquipmentResponsible() {

        Long equipmentResponsibleId = 1L;

        // Mock para o equipamento responsável inativo
        EquipmentResponsible equipmentResponsible = new EquipmentResponsible();
        equipmentResponsible.setId_equip_resp(equipmentResponsibleId);
        equipmentResponsible.setOperative(false);

        when(equipmentResponsibleRepository.findById(equipmentResponsibleId)).thenReturn(Optional.of(equipmentResponsible));

        // Executa a ativação
        equipmentResponsibleServiceImp.activateEquipmentResponsible(equipmentResponsibleId);

        // Verificações
        assertTrue(equipmentResponsible.isOperative(), "O responsável pelo equipamento deve estar ativo após a ativação");
        verify(equipmentResponsibleRepository).findById(equipmentResponsibleId);
    }

    @Test
    void inactivateEquipmentResponsible() {

        Long equipmentResponsibleId = 1L;

        // Mock para o equipamento responsável inativo
        EquipmentResponsible equipmentResponsible = new EquipmentResponsible();
        equipmentResponsible.setId_equip_resp(equipmentResponsibleId);
        equipmentResponsible.setOperative(true);

        when(equipmentResponsibleRepository.findById(equipmentResponsibleId)).thenReturn(Optional.of(equipmentResponsible));

        // Executa a ativação
        equipmentResponsibleServiceImp.inactivateEquipmentResponsible(equipmentResponsibleId);

        // Verificações
        assertFalse(equipmentResponsible.isOperative(), "O responsável pelo equipamento deve estar inativo após a operação");
        verify(equipmentResponsibleRepository).findById(equipmentResponsibleId);
    }
}