package com.MapView.BackEnd.services;

import com.MapView.BackEnd.dtos.Responsible.ResponsibleCrateDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.dtos.User.UserCreateDTO;
import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import com.MapView.BackEnd.entities.Classes;
import com.MapView.BackEnd.entities.Responsible;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.repository.ClassesRepository;
import com.MapView.BackEnd.repository.ResponsibleRepository;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.serviceImp.ResponsibleServiceImp;
import com.MapView.BackEnd.serviceImp.UserServiceImp;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach; // AQUI ESTÁ A IMPORTAÇÃO
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TesteServiceSOS {

    @InjectMocks
    private ResponsibleServiceImp responsibleServiceImp;

    @Mock
    private ResponsibleRepository responsibleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClassesRepository classesRepository;

    @Mock
    private UserServiceImp userServiceImp;

    @Mock
    private UserLogRepository userLogRepository; // se apagar os metodos param de funcionar (???)

    @Autowired
    EntityManager entityManager;

    private Long userLogId;
    private Long responsibleId;
    private Long classesId;
    private Users user;
    private Classes classes;
    private Responsible responsible;
    private ResponsibleCrateDTO responsibleDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicia mocks

        // Definir IDs de teste
        userLogId = 1L;
        classesId = 1L;

        // Mock para o usuario
        Users user1 = new Users();
        user1.setId_user(userLogId);
        user1.setOperative(true);

        // Mock para as classes
        Classes classes1 = new Classes();
        classes1.setId_classes(classesId);
        classes1.setOperative(true);

        // Configurando comportamento esperado do repositório de usuários
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user1));
        when(classesRepository.findById(classesId)).thenReturn(Optional.of(classes1));

    }

    @Test
    void createResponsible() {

        responsibleId = 1L;

        responsibleDTO = new ResponsibleCrateDTO(
                "Maria", "92903520", classesId, userLogId
        );

        Responsible responsible = new Responsible();
        responsible.setId_responsible(responsibleId);
        responsible.setResponsible("Maria");
        responsible.setEdv("92903520");
        responsible.setClasses(classes);
        responsible.setUser(user);
        responsible.setOperative(true);

        when(responsibleRepository.save(any(Responsible.class))).thenReturn(responsible);

        System.out.println("Users Log id"+userLogId);

        ResponsibleDetailsDTO result = responsibleServiceImp.createResposible(responsibleDTO, userLogId);

        assertNotNull(result);
        assertEquals("Maria", result.responsible_name());
        assertEquals("92903520", result.edv());

        verify(responsibleRepository, times(1)).save(any(Responsible.class));
    }


}
