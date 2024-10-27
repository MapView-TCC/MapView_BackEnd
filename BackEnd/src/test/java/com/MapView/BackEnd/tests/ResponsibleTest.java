package com.MapView.BackEnd.tests;

import com.MapView.BackEnd.dtos.Responsible.ResponsibleCrateDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.entities.Classes;
import com.MapView.BackEnd.entities.Responsible;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.repository.ClassesRepository;
import com.MapView.BackEnd.repository.ResponsibleRepository;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.serviceImp.ResponsibleServiceImp;
import jakarta.transaction.Transactional;
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
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Usar o banco H2 real
@Transactional
public class ResponsibleTest {

    @InjectMocks
    private ResponsibleServiceImp responsibleServiceImp;

    @Mock
    private ResponsibleRepository responsibleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClassesRepository classesRepository;

    @Mock
    private UserLogRepository userLogRepository;

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

        // Verificações
        assertNotNull(result);
        assertEquals("Maria", result.responsible_name());
        assertEquals("92903520", result.edv());

        // Verificando se o repositório de responsáveis foi chamado uma vez
        verify(responsibleRepository, times(1)).save(any(Responsible.class));
    }

}
