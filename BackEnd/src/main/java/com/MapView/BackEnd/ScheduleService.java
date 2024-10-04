package com.MapView.BackEnd;

import com.MapView.BackEnd.dtos.Notification.NotificationCreateDTO;
import com.MapView.BackEnd.dtos.Notification.NotificationDetailsDTO;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.Notification;
import com.MapView.BackEnd.entities.TrackingHistory;
import com.MapView.BackEnd.repository.EquipmentRepository;
import com.MapView.BackEnd.repository.NotificationRepository;
import com.MapView.BackEnd.serviceImp.NotificationServiceImp;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component

public class ScheduleService {

    private final EquipmentRepository equipmentRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationServiceImp notificationServiceImp;

    public ScheduleService(EquipmentRepository equipmentRepository, NotificationRepository notificationRepository, NotificationServiceImp notificationServiceImp) {
        this.equipmentRepository = equipmentRepository;
        this.notificationRepository = notificationRepository;
        this.notificationServiceImp = notificationServiceImp;
    }

    // toda a segunda feira ele vai fazer essa função
    @Scheduled(cron = "0 0 0 * * MON")
    public void executed() {
        List<Equipment> equipmentList = equipmentRepository.findAllByOperativeTrue();

        for (Equipment e : equipmentList) {
            LocalDate validity = e.getValidity();

            if (validity != null && LocalDate.now().getYear() == validity.getYear()) {

                if (getTrimestre(validity.getMonthValue()) == getTrimestre(LocalDate.now().getMonthValue())) {

                    TrackingHistory trackingHistory = new TrackingHistory();

                    NotificationCreateDTO notification = new NotificationCreateDTO(e.getIdEquipment());
                    notificationServiceImp.createNotification(notification);
                }
            }
        }
    }

    // Funções para excluir as notificações depois de um certo periodo

    //@Scheduled(cron = "0 0 0 1 1,4,7,10 *") // Executa à meia-noite no dia 1 dos meses 1, 4, 7 e 10, a cada 3 meses
    @Scheduled(cron = "0/1 * * * * *")
    public void deleteNotification() {
        System.out.println("to aqui");
        List<Notification> notificationList = notificationRepository.findAll();
        for (Notification notification: notificationList){
            // pegar o mes do notification
            int notificatioMonth = notification.getDate_notification().getMonthValue() + 3;

            // pegar o mes atual
            int mesatual = LocalDate.now().getMonthValue();

            if (notificatioMonth == mesatual){
                notificationServiceImp.deleteNotificationById(notification.getId_notification());
                System.out.println("Executou função");
            }
        }
    }

    // Função para calcular o trimestre baseado no mês
    public static int getTrimestre(int month) {
        return (month - 1) / 3 + 1;
    }

}
