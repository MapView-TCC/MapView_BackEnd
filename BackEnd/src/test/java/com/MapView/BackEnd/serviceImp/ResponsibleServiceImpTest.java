package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Responsible.ResponsibleCrateDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleUpdateDTO;
import com.MapView.BackEnd.entities.Classes;
import com.MapView.BackEnd.entities.Responsible;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import com.MapView.BackEnd.repository.ClassesRepository;
import com.MapView.BackEnd.repository.ResponsibleRepository;
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
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ResponsibleServiceImpTest {

    @InjectMocks
    private ResponsibleServiceImp responsibleServiceImp;

    @Mock
    private ResponsibleRepository responsibleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClassesRepository classesRepository;

    @Mock
    private UserLogRepository userLogRepository; // se apagar os metodos param de funcionar (???)

    @Test
    void createResponsible() {
        // Definir IDs de teste
        Long userLogId = 1L;
        Long responsibleId = 1L;
        Long classesId = 1L;

        // Mock para o usuario
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        // Mock para as classes
        Classes classes = new Classes();
        classes.setId_classes(classesId);
        classes.setOperative(true);


        // Configurando comportamento esperado do repositório de usuários
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));
        when(classesRepository.findById(classesId)).thenReturn(Optional.of(classes));

        // Criando o DTO responsável
        ResponsibleCrateDTO responsibleDTO = new ResponsibleCrateDTO(
                "Maria", "92903520", classesId, userLogId
        );

        // Mock para o responsável a ser criado
        Responsible responsible = new Responsible();
        responsible.setId_responsible(responsibleId);
        responsible.setResponsible("Maria");
        responsible.setEdv("92903520");
        responsible.setClasses(classes);
        responsible.setUser(user);
        responsible.setOperative(true);

        // Configurando o comportamento do repositório de responsáveis para salvar o responsável
        when(responsibleRepository.save(any(Responsible.class))).thenReturn(responsible);

        // Chamando o método para testar
        ResponsibleDetailsDTO result = responsibleServiceImp.createResposible(responsibleDTO, userLogId);

        System.out.println(result);

        // Verificações
        assertNotNull(result);
        assertEquals("Maria", result.responsible());
        assertEquals("92903520", result.edv());

        // Verificando se o repositório de responsáveis foi chamado uma vez
        verify(responsibleRepository, times(1)).save(any(Responsible.class));
    }

    @Test
    void findAllResponsible() {
        // Definir IDs de teste
        Long userLogId = 1L;
        Long responsibleId = 1L;
        Long classesId = 1L;

        // Mock para o usuario
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        // Configurar o comportamento do repositório para retornar o usuário
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Mock para as classes
        Classes classes = new Classes();
        classes.setId_classes(classesId);
        classes.setOperative(true);

        // Configurar o comportamento do repositório para retornar a classe
        when(classesRepository.findById(classesId)).thenReturn(Optional.of(classes));

        // Mock para o responsável a ser listado
        Responsible responsible = new Responsible();
        responsible.setId_responsible(responsibleId);
        responsible.setResponsible("Maria");
        responsible.setEdv("92903520");
        responsible.setClasses(classes);
        responsible.setUser(user);
        responsible.setOperative(true);

        // Configurar o comportamento do repositório para retornar o responsável
        when(responsibleRepository.findByOperativeTrue()).thenReturn(Collections.singletonList(responsible));

        // Chamando o método para testar
        List<ResponsibleDetailsDTO> result = responsibleServiceImp.getAllResposible(userLogId);

        System.out.println(result);

        // Verificações
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Maria", result.get(0).responsible());
        assertEquals("92903520", result.get(0).edv());
    }

    @Test
    void GetResponsible(){
        // Definir IDs de teste
        Long userLogId = 1L;
        Long responsibleId = 1L;
        Long classesId = 1L;

        // Mock para o usuario
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        // Configurar o comportamento do repositório para retornar o usuário
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Mock para as classes
        Classes classes = new Classes();
        classes.setId_classes(classesId);
        classes.setOperative(true);

        // Configurar o comportamento do repositório para retornar a classe
        when(classesRepository.findById(classesId)).thenReturn(Optional.of(classes));

        // Mock para o responsável a ser listado
        Responsible responsible = new Responsible();
        responsible.setId_responsible(responsibleId);
        responsible.setResponsible("Maria");
        responsible.setEdv("92903520");
        responsible.setClasses(classes);
        responsible.setUser(user);
        responsible.setOperative(true);

        when(responsibleRepository.findById(responsibleId)).thenReturn(Optional.of(responsible));

        ResponsibleDetailsDTO result  = responsibleServiceImp.getResposibleById(responsibleId, userLogId);
        System.out.println(result);

        // Verificações
        assertNotNull(result);
        assertEquals("Maria", result.responsible());
        assertEquals("92903520", result.edv());
    }

    @Test
    void updateResponsible() {
        // Definir IDs de teste
        Long userLogId = 1L;
        Long responsibleId = 1L;
        Long classesId = 1L;

        // Mock para o usuario
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        // Configurar o comportamento do repositório para retornar o usuário
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Mock para as classes
        Classes classes = new Classes();
        classes.setId_classes(classesId);
        classes.setOperative(true);

        // Configurar o comportamento do repositório para retornar a classe
        when(classesRepository.findById(classesId)).thenReturn(Optional.of(classes));

        // Mock para o responsável a ser listado
        Responsible responsible = new Responsible();
        responsible.setId_responsible(responsibleId);
        responsible.setResponsible("Maria");
        responsible.setEdv("92903520");
        responsible.setClasses(classes);
        responsible.setUser(user);
        responsible.setOperative(true);

        when(responsibleRepository.findById(responsibleId)).thenReturn(Optional.of(responsible));
        System.out.println(responsible);

        ResponsibleUpdateDTO updateDTO = new ResponsibleUpdateDTO("nome novo", "92903533", classesId, userLogId);
        ResponsibleDetailsDTO result = responsibleServiceImp.updateResposible(responsibleId, updateDTO, userLogId);
        System.out.println(result);

        assertNotNull(result);
        verify(responsibleRepository).save(responsible);
    }

    @Test
    void activeResponsible() {
        // Definir IDs de teste
        Long userLogId = 1L;
        Long responsibleId = 1L;
        Long classesId = 1L;

        // Mock para o usuario
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        // Configurar o comportamento do repositório para retornar o usuário
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Mock para as classes
        Classes classes = new Classes();
        classes.setId_classes(classesId);
        classes.setOperative(true);

        // Configurar o comportamento do repositório para retornar a classe
        when(classesRepository.findById(classesId)).thenReturn(Optional.of(classes));

        // Mock para o responsável a ser listado
        Responsible responsible = new Responsible();
        responsible.setId_responsible(responsibleId);
        responsible.setResponsible("Maria");
        responsible.setEdv("92903520");
        responsible.setClasses(classes);
        responsible.setUser(user);
        responsible.setOperative(false);

        when(responsibleRepository.findById(responsibleId)).thenReturn(Optional.of(responsible));
        responsibleServiceImp.activeResposible(responsibleId,userLogId);
        // Verifica se a classe agora está activa
        assertTrue(responsible.isOperative());
        // Verifica se o método save foi chamado no repositório de logs de usuário
        verify(userLogRepository).save(any(UserLog.class));
    }

    @Test
    void inactiveResponsible() {
        // Definir IDs de teste
        Long userLogId = 1L;
        Long responsibleId = 1L;
        Long classesId = 1L;

        // Mock para o usuario
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        // Configurar o comportamento do repositório para retornar o usuário
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Mock para as classes
        Classes classes = new Classes();
        classes.setId_classes(classesId);
        classes.setOperative(true);

        // Configurar o comportamento do repositório para retornar a classe
        when(classesRepository.findById(classesId)).thenReturn(Optional.of(classes));

        // Mock para o responsável a ser listado
        Responsible responsible = new Responsible();
        responsible.setId_responsible(responsibleId);
        responsible.setResponsible("Maria");
        responsible.setEdv("92903520");
        responsible.setClasses(classes);
        responsible.setUser(user);
        responsible.setOperative(true);

        when(responsibleRepository.findById(responsibleId)).thenReturn(Optional.of(responsible));
        responsibleServiceImp.inactivateResposible(responsibleId,userLogId);
        // Verifica se a classe agora está activa
        assertFalse(responsible.isOperative());
        // Verifica se o método save foi chamado no repositório de logs de usuário
        verify(userLogRepository).save(any(UserLog.class));
    }

    // casos para "foçar" o erro

    @Test
    void createResponsible_UserNotFound() {
        Long userLogId = 1L;
        Long classesId = 1L;

        // Configurar comportamento para retornar usuário inexistente
        when(userRepository.findById(userLogId)).thenReturn(Optional.empty());

        // Cria o DTO de entrada
        ResponsibleCrateDTO responsibleDTO = new ResponsibleCrateDTO("Maria", "92903520", classesId, userLogId);

        // Verifica se a exceção é lançada
        assertThrows(NotFoundException.class, () -> responsibleServiceImp.createResposible(responsibleDTO, userLogId));
    }

    @Test
    void createResponsible_ClassNotFound() {
        Long userLogId = 1L;
        Long classesId = 1L;

        // Mock para o usuário
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        // Configura o usuário válido
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Simula a classe como inexistente
        when(classesRepository.findById(classesId)).thenReturn(Optional.empty());

        // Cria o DTO de entrada
        ResponsibleCrateDTO responsibleDTO = new ResponsibleCrateDTO("Maria", "92903520", classesId, userLogId);

        // Verifica se a exceção NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> responsibleServiceImp.createResposible(responsibleDTO, userLogId));
    }

    @Test
    void updateResponsible_NotFound() {
        Long responsibleId = 1L;
        Long userLogId = 1L;

        // Configura o retorno do repositório com Optional.empty() para o responsável
        when(responsibleRepository.findById(responsibleId)).thenReturn(Optional.empty());

        ResponsibleUpdateDTO updateDTO = new ResponsibleUpdateDTO("nome novo", "92903533", 1L, userLogId);

        assertThrows(NotFoundException.class, () -> responsibleServiceImp.updateResposible(responsibleId, updateDTO, userLogId));
    }

    @Test
    void activeResponsible_UserNotOperative() {
        Long responsibleId = 1L;
        Long userLogId = 1L;

        // Usuário está inativo
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(false);

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        assertThrows(NotFoundException.class, () -> responsibleServiceImp.activeResposible(responsibleId, userLogId));
    }

    @Test
    void inactivateResponsible_UserNotOperative() {
        Long responsibleId = 1L;
        Long userLogId = 1L;

        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(false);

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        assertThrows(NotFoundException.class, () -> responsibleServiceImp.inactivateResposible(responsibleId, userLogId));
    }
}