package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.Notification.NotificationCreateDTO;
import com.MapView.BackEnd.dtos.Notification.NotificationDetailsDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "notification")
@Entity(name = "notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_notification")
public class Notification {

    // fazer um getAll, delete

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_notification;

    private LocalDate date_notification;

    @ManyToOne
    @JoinColumn(name = "id_equipment")
    private Equipment equipment;

    public Notification(Equipment data) {
        this.equipment = data;
        this.date_notification = LocalDate.now();
    }
}
