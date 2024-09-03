package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.enums.EnumColors;
import com.MapView.BackEnd.enums.EnumTrackingAction;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Table(name = "tracking_history")
@Entity(name = "tracking_history")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id_tracking")
public class TrackingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tracking;

    @CreationTimestamp
    // Use aspas invertidas para forçar o nome exato
    //@Column(updatable = false) // Se você não quiser que a data de criação seja atualizada
    private Instant datetime;

    @OneToOne
    @JoinColumn(name ="id_equipment")
    private Equipment id_equipment;
    @OneToOne
    @JoinColumn(name ="id_environment")
    private Enviroment id_enviroment;

    @Enumerated(value = EnumType.STRING)
    private EnumTrackingAction action;

    @Enumerated(value = EnumType.STRING)
    private EnumColors colors;

    public TrackingHistory(Long id_tracking, Instant dateTime, Equipment id_equipment,
                           Enviroment id_enviroment,
                           EnumTrackingAction action, EnumColors colors) {
        this.id_tracking = id_tracking;
        this.datetime = dateTime;
        this.id_equipment = id_equipment;
        this.id_enviroment = id_enviroment;
        this.action = action;
        this.colors = colors;
    }
}
