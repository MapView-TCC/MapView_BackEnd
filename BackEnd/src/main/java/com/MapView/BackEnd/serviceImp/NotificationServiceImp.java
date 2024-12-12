package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Notification.NotificationCreateDTO;
import com.MapView.BackEnd.dtos.Notification.NotificationDetailsDTO;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Notification;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import com.MapView.BackEnd.repository.EquipmentRepository;
import com.MapView.BackEnd.repository.NotificationRepository;
import com.MapView.BackEnd.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
                new NotFoundException("Notification with ID " + id_notification + " not found. Deletion cannot proceed."));
        if (notification != null){
            notificationRepository.deleteById(id_notification);
        }
    }

    @Override
    public NotificationDetailsDTO getNotification(Long id_notification) {
        Notification notification = this.notificationRepository.findById(id_notification)
                .orElseThrow(() -> new NotFoundException("Notification with ID " + id_notification + " not found. Deletion cannot proceed."));
        System.out.println(notification);
        return new NotificationDetailsDTO(notification);

    }

    @Override
    public NotificationDetailsDTO createNotification(NotificationCreateDTO notificationCreateDTO) {
        // Buscar o equipamento usando o ID fornecido no DTO
        Equipment equipment = equipmentRepository.findByCode(notificationCreateDTO.code())
                .orElseThrow(() -> new NotFoundException("Equipment not found with code: " + notificationCreateDTO.code()));

        // Criar a nova notificação
        Notification notification = new Notification(equipment);
        notification.setDate_notification(LocalDate.now());

        // Salvar a notificação no repositório
        Notification savedNotification = notificationRepository.save(notification);

        // Retornar o DTO com os detalhes da notificação criada
        return new NotificationDetailsDTO(savedNotification);
    }

}
