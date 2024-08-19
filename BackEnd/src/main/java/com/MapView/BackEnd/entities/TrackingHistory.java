package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.enums.EnumAction;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Table(name = "tracking_history")
@Entity(name = "tracking_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_tracking")
public class TrackingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tracking;
    @CreationTimestamp
    private Instant dateTime;
    @OneToOne
    @JoinColumn(name ="id_equipment")
    private Equipment id_equipment;
    @OneToOne
    @JoinColumn(name ="id_enviroment")
    private Enviroment id_enviroment;

    @Enumerated
    private EnumAction action;

}
