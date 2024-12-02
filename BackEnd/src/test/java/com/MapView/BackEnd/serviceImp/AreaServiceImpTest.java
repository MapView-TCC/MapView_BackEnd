package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Area.AreaCreateDTO;
import com.MapView.BackEnd.dtos.Area.AreaDetailsDTO;
import com.MapView.BackEnd.dtos.Area.AreaUpdateDTO;
import com.MapView.BackEnd.entities.Area;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import com.MapView.BackEnd.repository.AreaRepository;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Usar o banco H2 real, usa um banco de dados real em vez de um em memória
@ActiveProfiles("test") // configurar o banco de dados H2
@Transactional
public class AreaServiceImpTest {

    @InjectMocks
    private AreaServiceImp areaServiceImp; // Classe a ser testada, com os mocks injetados

    @Mock
    private AreaRepository areaRepository;

    @Mock
    private UserLogRepository userLogRepository; // Mock da interface de repositório para logs de usuário

    @Mock
    private UserRepository userRepository;

    @Mock
    private Users user; // Simulação de um objeto usuário

    @Test
    public void createArea_UserNotFound_ShouldThrowNotFoundException() {
        // ARRANGE
        AreaCreateDTO areaCreateDTO = new AreaCreateDTO("Teste codigo", "teste nome");
        when(userLogRepository.findById(1L)).thenReturn(Optional.empty()); // Simula que o log do usuário não foi encontrado

        // ACT & ASSERT
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            areaServiceImp.createArea(areaCreateDTO, 1L); // Tenta criar a área
        });

        assertEquals("User with ID 1 not found.", exception.getMessage()); // Verifica se a mensagem da exceção é correta
    }

    @Test
    void testGetAreaSuccess() {
        Long userLogId = 1L;
        Long areaId = 1L;

        // Mock do usuário
        Users user = new Users(); // Defina suas propriedades necessárias, cria um usuário simulado
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user)); // Simula que o usuário foi encontrado

        // Mock da área
        Area area = new Area();
        area.setId_area(areaId);
        area.setCode("Teste codigo");
        area.setArea_name("Teste nome");
        area.setOperative(true);
        when(areaRepository.findById(areaId)).thenReturn(Optional.of(area));

        // Chamada do método
        AreaDetailsDTO result = areaServiceImp.getArea(userLogId, areaId); // Obtém os detalhes da área

        // Verificações
        assertNotNull(result); // Verifica se o resultado não é nulo
        assertEquals("Teste nome", result.area_name()); // Verifica o nome da área
        assertEquals("Teste codigo", result.area_code()); // Verifica o código da área
    }

    @Test
    void testGetArea_NotFound() {
        Long userLogId = 1L;
        Long areaId = 1L;

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user)); // Simula que o usuário foi encontrado
        when(areaRepository.findById(areaId)).thenReturn(Optional.empty()); // Simula que a área não foi encontrada

        Exception exception = assertThrows(NotFoundException.class, () -> {
            areaServiceImp.getArea(userLogId, areaId);
        });  // Tenta obter a área

        assertEquals("Area with ID 1 not found.", exception.getMessage()); // Verifica a mensagem da exceção
    }

    @Test
    void testCreateAreaSuccess() {
        AreaCreateDTO createDTO = new AreaCreateDTO("CA600", "Área A"); // Criação do DTO para área
        Long userLogId = 1L;
        Users user = new Users(); // simulação do usuário

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        // Criar a instância da área com ID definido
        Area savedArea = new Area();
        savedArea.setId_area(1L); // Defina um ID para a área salva
        savedArea.setCode(createDTO.area_code());
        savedArea.setArea_name(createDTO.area_name());
        savedArea.setOperative(true); // Defina como necessário

        when(areaRepository.save(any(Area.class))).thenReturn(savedArea);

        AreaDetailsDTO result = areaServiceImp.createArea(createDTO, userLogId);

        assertNotNull(result);
        assertEquals("Área A", result.area_name());
        assertEquals("CA600", result.area_code());
        verify(userLogRepository).save(any(UserLog.class));

        System.out.println(result);
    }


    @Test
    void testUpdateArea_Success() {
        Long areaId = 1L; // ID da área a ser atualizada
        AreaUpdateDTO updateDTO = new AreaUpdateDTO("New Area", "CA601");  // DTO com novos dados da área
        Area area = new Area();
        area.setId_area(areaId);
        area.setArea_name("Old Area"); // Define o nome antigo da área
        area.setCode("CA600"); // Define o código antigo da área
        area.setOperative(true);

        when(areaRepository.findById(areaId)).thenReturn(Optional.of(area));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        AreaDetailsDTO result = areaServiceImp.updateArea(areaId, updateDTO, 1L); // Chama o método para atualizar a área

        System.out.println("Area name: " + result.area_name());
        System.out.println("Area code: " + result.area_code());

        // Verifique os valores atualizados
        assertNotNull(result);
        assertEquals("New Area", result.area_code()); // Verifica o nome da área
        assertEquals("CA601", result.area_name()); // Verifica o código da área

        // Verifica se o log foi salvo
        verify(userLogRepository).save(any(UserLog.class));
    }

    @Test
    void testActivateArea_AreaNotFound() {
        Long areaId = 1L; // ID da área a ser ativada
        Long userLogId = 2L; // ID do usuário que está ativando a área

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user)); // Simula que o usuário foi encontrado
        when(areaRepository.findById(areaId)).thenReturn(Optional.empty()); // Simula que a área não foi encontrada

        // Verifica se uma exceção é lançada quando a área não é encontrada
        assertThrows(NotFoundException.class, () -> {
            areaServiceImp.activateArea(areaId, userLogId);
        });
    }

    @Test
    void testInactivateArea_AreaNotFound() {
        Long areaId = 1L; // ID da área a ser ativada
        Long userLogId = 2L; // ID do usuário que está ativando a área

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));
        when(areaRepository.findById(areaId)).thenReturn(Optional.empty());

        // Verifica se uma exceção é lançada quando a área não é encontrada
        assertThrows(NotFoundException.class, () -> {
            areaServiceImp.inactivateArea(areaId, userLogId); // Tenta desativar a área
        });
    }

}