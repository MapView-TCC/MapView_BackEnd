package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Notification.NotificationCreateDTO;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Notification;
import com.MapView.BackEnd.entities.TrackingHistory;
import com.MapView.BackEnd.repository.EquipmentRepository;
import com.MapView.BackEnd.repository.NotificationRepository;
import com.MapView.BackEnd.repository.TrackingHistoryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test") // configurar o banco de dados H2
@Transactional
public class ScheduleServiceImpTest {

    @InjectMocks
    private ScheduleServiceImp scheduleServiceImp;  // ScheduleServiceImp vai usar os mocks

    @Mock
    private NotificationServiceImp notificationServiceImp;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private TrackingHistoryRepository trackingHistoryRepository;

    @Mock
    private TrackingHistoryServiceImp trackingHistoryServiceImp;

    @Mock
    private EquipmentRepository equipmentRepository;

    // Schedule
    @Test
    void testDeleteNotification() {
        Long notificationId1 = 1L;
        Long notificationId2 = 2L;

        // Criação das notificações
        Notification notification1 = new Notification();
        notification1.setId_notification(notificationId1);
        notification1.setDate_notification(LocalDate.now().minusMonths(4)); // 4 meses atrás

        Notification notification2 = new Notification();
        notification2.setId_notification(notificationId2);
        notification2.setDate_notification(LocalDate.now().minusMonths(2)); // 2 meses atrás

        // Simular retorno de todas as notificações
        List<Notification> notifications = Arrays.asList(notification1, notification2);
        when(notificationRepository.findAll()).thenReturn(notifications);

        // Chama o método deleteNotification no ScheduleService
        scheduleServiceImp.deleteNotification();

        // Verifica se o método de exclusão foi chamado corretamente para a notificação 1 (4 meses atrás)
        verify(notificationServiceImp, times(1)).deleteNotificationById(notificationId1);

        // Verifica se o método de exclusão NÃO foi chamado para a notificação 2 (2 meses atrás)
        verify(notificationServiceImp, never()).deleteNotificationById(notificationId2);
    }

    // Schedule do Tracking history
    @Test
    void testDeleteTracking(){
        Long trackingId1 = 1L;
        Long trackingId2 = 2L;


        // criar os tracking history
        TrackingHistory trackingHistory1 = new TrackingHistory();
        trackingHistory1.setId(trackingId1);
        trackingHistory1.setDatetime(LocalDateTime.now().minusMonths(13));  // 13 meses atrás

        TrackingHistory trackingHistory2 = new TrackingHistory();
        trackingHistory2.setId(trackingId2);
        trackingHistory2.setDatetime(LocalDateTime.now().minusMonths(11));  // 11 meses atrás

        // Simular o retorno do repositório com as duas instâncias
        List<TrackingHistory> trackingHistories = Arrays.asList(trackingHistory1, trackingHistory2);
        when(trackingHistoryRepository.findAll()).thenReturn(trackingHistories);

        // Executar manualmente o método agendado
        scheduleServiceImp.deleteTrackingHistory();

        // Verificar se o método delete foi chamado para o registro com mais de um ano
        verify(trackingHistoryServiceImp, times(1)).deleteTrackingById(trackingId1);

        // Verificar que o método delete não foi chamado para o registro com menos de um ano
        verify(trackingHistoryServiceImp, never()).deleteTrackingById(trackingId2);
    }

    @Test
    void testCreateNotification() {

        Long id_equipment = 1L;

        // Cria um equipamento com validade no ano atual e no mesmo trimestre
        Equipment equipment = new Equipment();
        equipment.setId_equipment(id_equipment);
        equipment.setCode("E001");
        equipment.setValidity(LocalDate.now()); // data de validade é hj

        // Simula o retorno do repositório com o equipamento
        when(equipmentRepository.findAllByOperativeTrue()).thenReturn(Collections.singletonList(equipment));

        // Executa o método que deve criar a notificação
        scheduleServiceImp.createNotification();

        // Verifica se a notificação foi criada
        verify(notificationServiceImp, times(1)).createNotification(any(NotificationCreateDTO.class));
    }

    @Test
    void testCreateNotification_NoEquipment() {
        // Simula o retorno do repositório sem equipamentos operacionais
        when(equipmentRepository.findAllByOperativeTrue()).thenReturn(Collections.emptyList());

        // Executa o método que deve criar a notificação
        scheduleServiceImp.createNotification();

        // Verifica que o método de criação de notificações não foi chamado
        verify(notificationServiceImp, never()).createNotification(any(NotificationCreateDTO.class));
    }

    @Test
    void testCreateNotification_EquipmentNotInCurrentTrimestre() {
        // Cria um equipamento com validade no ano atual, mas em outro trimestre
        Long id_equipment = 1L;

        Equipment equipment = new Equipment();
        equipment.setId_equipment(id_equipment);
        equipment.setCode("E001");
        equipment.setValidity(LocalDate.now().plusMonths(5)); // Supondo que está fora do trimestre atual

        // Simula o retorno do repositório com o equipamento
        when(equipmentRepository.findAllByOperativeTrue()).thenReturn(Collections.singletonList(equipment));

        // Executa o método que deve criar a notificação
        scheduleServiceImp.createNotification();

        // Verifica que o método de criação de notificações não foi chamado
        verify(notificationServiceImp, never()).createNotification(any(NotificationCreateDTO.class));
    }

}
