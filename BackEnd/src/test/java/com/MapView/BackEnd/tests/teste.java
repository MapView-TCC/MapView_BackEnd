package com.MapView.BackEnd.tests;

import com.MapView.BackEnd.dtos.Responsible.ResponsibleCrateDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.entities.Classes;
import com.MapView.BackEnd.entities.Responsible;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.repository.ClassesRepository;
import com.MapView.BackEnd.repository.ResponsibleRepository;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.serviceImp.ResponsibleServiceImp;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class teste {

    @InjectMocks
    private ResponsibleServiceImp responsibleServiceImp;

    @Mock
    private ResponsibleRepository responsibleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClassesRepository classesRepository;

    private Long userLogId;
    private Long responsibleId;
    private Long classesId;
    private Users user;
    private Classes classes;
    private Responsible responsible;

    @BeforeEach
    public void setUp() {
        // Definir IDs de teste
        userLogId = 1L;
        responsibleId = 1L;
        classesId = 1L;

        // Mock para o usuário
        user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        // Configurar o comportamento do repositório de usuários
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Mock para as classes
        classes = new Classes();
        classes.setId_classes(classesId);
        classes.setOperative(true);

        // Configurar o comportamento do repositório de classes
        when(classesRepository.findById(classesId)).thenReturn(Optional.of(classes));

        // Mock para o responsável
        responsible = new Responsible();
        responsible.setId_responsible(responsibleId);
        responsible.setResponsible("Maria");
        responsible.setEdv("92903520");
        responsible.setClasses(classes);
        responsible.setUser(user);
        responsible.setOperative(true);

        // Configurar o comportamento do repositório de responsáveis
        when(responsibleRepository.findById(responsibleId)).thenReturn(Optional.of(responsible));
    }

    @Test
    void createResponsible() {
        ResponsibleCrateDTO responsibleDTO = new ResponsibleCrateDTO("Maria", "92903520", classesId, userLogId);

        // Chamando o método para testar
        ResponsibleDetailsDTO result = responsibleServiceImp.createResposible(responsibleDTO, userLogId);

        // Verificações
        assertNotNull(result);
        assertEquals("Maria", result.responsible_name());
        assertEquals("92903520", result.edv());

        // Verificando se o repositório de responsáveis foi chamado uma vez
        verify(responsibleRepository, times(1)).save(any(Responsible.class));
    }
}
