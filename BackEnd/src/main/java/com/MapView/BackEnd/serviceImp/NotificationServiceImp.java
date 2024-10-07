package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Notification.NotificationCreateDTO;
import com.MapView.BackEnd.dtos.Notification.NotificationDetailsDTO;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Notification;
import com.MapView.BackEnd.infra.NotFoundException;
import com.MapView.BackEnd.repository.EquipmentRepository;
import com.MapView.BackEnd.repository.NotificationRepository;
import com.MapView.BackEnd.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImp implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final EquipmentRepository equipmentRepository;

    public NotificationServiceImp(NotificationRepository notificationRepository, EquipmentRepository equipmentRepository){
        this.notificationRepository = notificationRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public List<NotificationDetailsDTO> getAllNotification() {
        return notificationRepository.findAll().stream().map(NotificationDetailsDTO::new).toList();
    }

    @Override
    public void deleteNotificationById(Long id_notification) {
        var notification = notificationRepository.findById(id_notification).orElseThrow(() ->
                new NotFoundException("Id not found"));
        if (notification != null){
            notificationRepository.deleteById(id_notification);
        }
    }

    @Override
    public NotificationDetailsDTO getNotification(Long id_notification) {
        Notification notification = this.notificationRepository.findById(id_notification).orElseThrow(() -> new NotFoundException("Id notification not found!"));
        System.out.println(notification);
        return new NotificationDetailsDTO(notification);

    }

    @Override
    public NotificationDetailsDTO createNotification(NotificationCreateDTO data) {
        Equipment equipment = equipmentRepository.findById(data.id_equipment())
                .orElseThrow(() -> new RuntimeException("Id equipment não encontrado!"));

        Notification notification = notificationRepository.save(new Notification(equipment));

        return new NotificationDetailsDTO(notification);
    }

}
