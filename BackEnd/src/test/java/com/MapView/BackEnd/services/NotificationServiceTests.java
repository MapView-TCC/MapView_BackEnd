package com.MapView.BackEnd.services;

import com.MapView.BackEnd.dtos.Notification.NotificationCreateDTO;
import com.MapView.BackEnd.dtos.Notification.NotificationDetailsDTO;
import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.repository.EquipmentRepository;
import com.MapView.BackEnd.repository.NotificationRepository;
import com.MapView.BackEnd.serviceImp.NotificationServiceImp;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test") // configurar o banco de dados H2
@Transactional
public class NotificationServiceTests {

    @InjectMocks
    private NotificationServiceImp notificationServiceImp;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private EquipmentRepository equipmentRepository;



    @Test
    void testGetAllNotification() {

        // Definir IDs de teste
        Long postId = 1L;
        Long environmentId = 1L;

        // Inicializar um equipamento mockado
        Equipment equipment = new Equipment();
        equipment.setIdEquipment("E001"); // Definir um ID para o equipamento
        equipment.setName_equipment("Equipamento Teste");
        equipment.setType("Tipo novo");

        // Criar o post e o environment
        Environment environment = new Environment();
        environment.setId_environment(environmentId);
        environment.setEnvironment_name("Sala 2");

        Post post = new Post();
        post.setId_post(postId);
        post.setPost("Mesa 21");

        // Criar um objeto Location e associá-lo ao equipamento
        Location location = new Location();
        location.setEnvironment(environment); // Inicializar o ambiente com um nome
        location.setPost(post); // Inicializar o posto com um nome
        equipment.setLocation(location); // Associar a localização ao equipamento

        // Criar uma notificação usando o equipamento
        Notification notification = new Notification(equipment);
        notification.setId_notification(1L); // Definir um ID para a notificação
        notification.setDate_notification(LocalDate.now());

        // Configurar comportamento esperado do repositório de notificações
        when(notificationRepository.findAll()).thenReturn(Collections.singletonList(notification));

        // Chamar o método da instância real
        List<NotificationDetailsDTO> result = notificationServiceImp.getAllNotification();

        System.out.println(result);

        // Verificações
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(notification.getId_notification(), result.get(0).id_notification());
        assertEquals("Equipamento Teste", result.get(0).name_equipment());
        assertEquals("Tipo novo", result.get(0).type());
        assertEquals("Sala 2", result.get(0).environment_name());
        assertEquals("Mesa 21", result.get(0).post_name());
        assertEquals(notification.getId_notification(), result.get(0).id_notification());
    }

    @Test
    void testDeleteNotificationById() {
        Long notificationId = 1L;

        // Configurar uma notificação mockada
        Notification notification = new Notification();
        notification.setId_notification(notificationId);

        // Configurar o comportamento do repositório
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        // Chamar o método para deletar
        notificationServiceImp.deleteNotificationById(notificationId);

        // Verificar se o repositório de notificações chamou o método de deleteById
        verify(notificationRepository).deleteById(notificationId);
    }

    @Test
    void testNotificationsId(){

        Long notificationId = 1L;
        Long postId = 1L;
        Long environmentId = 1L;

        // Inicializar um equipamento mockado
        Equipment equipment = new Equipment();
        equipment.setIdEquipment("E001"); // Definir um ID para o equipamento
        equipment.setName_equipment("Equipamento Teste");
        equipment.setType("Tipo novo");

        // Criar o post e o environment
        Environment environment = new Environment();
        environment.setId_environment(environmentId);
        environment.setEnvironment_name("Sala 2");

        Post post = new Post();
        post.setId_post(postId);
        post.setPost("Mesa 21");

        // Criar um objeto Location e associá-lo ao equipamento
        Location location = new Location();
        location.setEnvironment(environment); // Inicializar o ambiente com um nome
        location.setPost(post); // Inicializar o posto com um nome
        equipment.setLocation(location); // Associar a localização ao equipamento

        // Criar uma notificação usando o equipamento
        Notification notification = new Notification(equipment);
        notification.setId_notification(notificationId); // Definir um ID para a notificação
        notification.setDate_notification(LocalDate.now());

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        // chamar o metodo para testar
        NotificationDetailsDTO result = notificationServiceImp.getNotification(notificationId);

        System.out.println(result);

        // Verificações
        assertNotNull(result);
    }

    @Test
    void testNotificationCreate() {
        Long notificationId = 1L;
        String equipmentId = "E001"; // Adicione um ID para o equipamento

        // Inicializar um equipamento mockado
        Equipment equipment = new Equipment();
        equipment.setIdEquipment("E001"); // Definir um ID para o equipamento
        equipment.setName_equipment("Equipamento Teste");
        equipment.setType("Tipo novo");

        // Criar o post e o environment
        Environment environment = new Environment();
        environment.setId_environment(1L);
        environment.setEnvironment_name("Sala 2");

        Post post = new Post();
        post.setId_post(1L);
        post.setPost("Mesa 21");

        // Criar um objeto Location e associá-lo ao equipamento
        Location location = new Location();
        location.setEnvironment(environment); // Inicializar o ambiente
        location.setPost(post); // Inicializar o posto
        equipment.setLocation(location); // Associar a localização ao equipamento

        // Criar um DTO para a notificação
        NotificationCreateDTO notificationCreateDTO = new NotificationCreateDTO(equipmentId); // Supondo que tenha um construtor que aceita um ID de equipamento

        // Configurar o comportamento esperado do repositório
        when(equipmentRepository.findById(equipmentId)).thenReturn(Optional.of(equipment)); // Mock do repositório de equipamentos

        // thenAnswer(...): Em vez de retornar um valor fixo, essa função permite definir uma lógica personalizada que será executada quando o metodo for chamado
        when(notificationRepository.save(any(Notification.class))).thenAnswer(invocation -> {
            Notification savedNotification = invocation.getArgument(0); // obtém o primeiro argumento passado para o método
            savedNotification.setId_notification(notificationId); // Atribuir o ID simulado
            return savedNotification;
        });

        // Chamar o método de criação da notificação
        NotificationDetailsDTO result = notificationServiceImp.createNotification(notificationCreateDTO);

        System.out.println(result);

        // Verificações
        assertNotNull(result);
        assertEquals(notificationId, result.id_notification()); // Agora isso deve ser 1
        assertEquals(equipment.getIdEquipment(), result.id_equipment());
        assertEquals(equipment.getName_equipment(), result.name_equipment());
        assertEquals(equipment.getType(), result.type());
        assertEquals(environment.getEnvironment_name(), result.environment_name());
        assertEquals(post.getPost(), result.post_name());
    }

}
