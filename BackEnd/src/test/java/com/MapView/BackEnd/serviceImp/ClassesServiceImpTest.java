package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Classes.ClassesCreateDTO;
import com.MapView.BackEnd.dtos.Classes.ClassesDetaiLDTO;
import com.MapView.BackEnd.dtos.Classes.ClassesUpdateDTO;
import com.MapView.BackEnd.entities.Classes;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.EnumCourse;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import com.MapView.BackEnd.repository.ClassesRepository;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
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
public class ClassesServiceImpTest {

    @InjectMocks
    private ClassesServiceImp classesServiceImp;

    @Mock
    private ClassesRepository classesRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserLogRepository userLogRepository;

    @Mock
    private Users users;

    @Test
    void testGetClasses(){
        // Definir IDs de teste
        Long userLogId = 1L;
        Long classesId = 1L;

        // Mock do usuário
        Users user = new Users();
        user.setId_user(userLogId); // Definindo ID do usuário mockado

        // Configurando comportamento esperado do repositório de usuários
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Mock da entidade Classes
        Classes classes = new Classes();
        classes.setId_classes(classesId);
        classes.setEnumCourse(EnumCourse.ADMINISTRACAO); // Exemplo de enum
        classes.setClasses("Teste");
        classes.setUser(user); // Definindo o usuário mockado
        classes.setCreation_date(LocalDate.of(2024, 9, 25)); // Definindo a data correta
        classes.setOperative(true);

        // Supondo que exista um serviço para buscar uma classe, pode-se chamá-lo e verificar o comportamento
        when(classesRepository.findById(classesId)).thenReturn(Optional.of(classes));

        // Chamada do método para testar
        ClassesDetaiLDTO result = classesServiceImp.getClasseById(classesId, userLogId);

        System.out.println(result);

        // Verificações
        assertNotNull(result);
        assertEquals(classesId, result.id_classes());
        assertEquals(EnumCourse.ADMINISTRACAO, result.enumCourse());
        assertEquals("Teste", result.classes());
        assertEquals(user, result.user());
        assertEquals(LocalDate.of(2024, 9, 25), result.creation_date());
    }

    @Test
    void testCreateArea(){
        // Definir IDs de teste
        Long userLogId = 1L;
        Long classesId = 1L;

        // Mock do usuário
        Users user = new Users();
        user.setId_user(userLogId); // Definindo ID do usuário mockado

        // Simulando a criação do DTO
        ClassesCreateDTO createDTO = new ClassesCreateDTO(EnumCourse.ADMINISTRACAO,
                "teste",
                user.getId_user(),
                LocalDate.of(2024, 9, 25));

        // o serviço de criação da classe sendo chamado
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        Classes createClass = new Classes();
        createClass.setId_classes(classesId);
        createClass.setEnumCourse(createDTO.enumCourse());
        createClass.setClasses(createClass.getClasses());
        createClass.setUser(user);
        createClass.setCreation_date(createDTO.creation_date());
        createClass.setOperative(true);

        // Simulando a criação no repositório
        when(classesRepository.save(any(Classes.class))).thenReturn(createClass);

        // chamando o metodo de criação da area
        ClassesDetaiLDTO result = classesServiceImp.createClasses(createDTO, userLogId);

        System.out.println(result);

        assertNotNull(result);
    }

    @Test
    void testGetAreaNotFound(){
        Long userlogId = 1L;
        Long classesId = 1L;

        when(userRepository.findById(userlogId)).thenReturn(Optional.of(users));
        when(classesRepository.findById(classesId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            classesServiceImp.getClasseById(classesId, userlogId);
        });

        assertEquals("Class with ID 1 not found", exception.getMessage());
    }

    // Teste do método getClasseByName
    @Test
    void testGetClasseByName() {
        Long userLogId = 1L;
        String className = "Teste Classe";

        Users user = new Users();
        user.setId_user(userLogId);

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        Classes classe = new Classes();
        classe.setClasses(className);
        classe.setUser(user);
        classe.setOperative(true);

        when(classesRepository.findByClasses(className)).thenReturn(Optional.of(classe));

        ClassesDetaiLDTO result = classesServiceImp.getClasseByName(className, userLogId);

        assertNotNull(result);
        assertEquals(className, result.classes());
    }


    @Test
    void testGetClasseByNameNotFound() {
        Long userLogId = 1L;
        String className = "Classe Inexistente";

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));
        when(classesRepository.findByClasses(className)).thenReturn(Optional.empty());

        ClassesDetaiLDTO result = classesServiceImp.getClasseByName(className, userLogId);
        assertNull(result);
    }

    // Teste do método updateClasses
    @Test
    void testUpdateClasses() {
        Long userLogId = 1L;
        Long classesId = 1L;

        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        Classes classe = new Classes();
        classe.setId_classes(classesId);
        classe.setClasses("Classe Original");
        classe.setUser(user);
        classe.setOperative(true);

        when(classesRepository.findById(classesId)).thenReturn(Optional.of(classe));

        ClassesUpdateDTO updateDTO = new ClassesUpdateDTO(EnumCourse.DIGITAL_SOLUTIONS, "Classe Atualizada", userLogId);
        ClassesDetaiLDTO result = classesServiceImp.updateClasses(classesId, updateDTO, userLogId);

        assertNotNull(result);
        assertEquals("Classe Atualizada", result.classes());
        verify(classesRepository).save(classe); // Verifica se o método save foi chamado
    }

    @Test
    void testUpdateClassesNotFound() {
        Long userLogId = 1L;
        Long classesId = 1L;

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(users));
        when(classesRepository.findById(classesId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () ->
                classesServiceImp.updateClasses(classesId, new ClassesUpdateDTO(EnumCourse.DIGITAL_SOLUTIONS, "teste", userLogId), userLogId)
        );

        assertEquals("Class with ID 1 not found", exception.getMessage());
    }

    // Teste do método getAllClasses
    @Test
    void testGetAllClasses() {
        Long userLogId = 1L;

        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        Classes classe = new Classes();
        classe.setClasses("Classe 1");
        classe.setUser(user);
        classe.setOperative(true);

        when(classesRepository.findClassesByOperativeTrue()).thenReturn(Collections.singletonList(classe));

        List<ClassesDetaiLDTO> result = classesServiceImp.getAllClasses(userLogId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Classe 1", result.get(0).classes());
    }

    // Teste do método activeClass
    @Test
    void testActiveClass() {
        Long classId = 1L;
        Long userLogId = 1L;

        // Mock do usuário, agora operativo
        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Mock da classe, inicialmente inativa
        Classes classe = new Classes();
        classe.setId_classes(classId);
        classe.setOperative(false); // Classe deve ser inativa para ser ativada

        when(classesRepository.findById(classId)).thenReturn(Optional.of(classe));

        // Chama o método para ativar a classe
        classesServiceImp.activeClass(classId, userLogId);

        // Verifica se a classe agora está activa
        assertTrue(classe.isOperative());
        // Verifica se o método save foi chamado no repositório de logs de usuário
        verify(userLogRepository).save(any(UserLog.class));
    }

    // Teste do método inactiveClass
    @Test
    void testInactiveClass() {
        Long classId = 1L;
        Long userLogId = 1L;

        Users user = new Users();
        user.setId_user(userLogId);
        user.setOperative(true);

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        Classes classe = new Classes();
        classe.setId_classes(classId);
        classe.setOperative(true);

        when(classesRepository.findById(classId)).thenReturn(Optional.of(classe));

        classesServiceImp.inactiveClass(classId, userLogId);

        assertFalse(classe.isOperative());
        verify(userLogRepository).save(any(UserLog.class)); // Verifica se o método save foi chamado
    }

}
