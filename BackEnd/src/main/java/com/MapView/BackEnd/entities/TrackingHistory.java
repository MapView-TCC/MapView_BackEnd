package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.enums.EnumColors;
import com.MapView.BackEnd.enums.EnumTrackingAction;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="id_equipment")
    private Equipment idEquipment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="id_environment")
    private Enviroment id_environment;

    @Enumerated(value = EnumType.STRING)
    private EnumTrackingAction action;

    @Enumerated(value = EnumType.STRING)
    private EnumColors warning;



    public TrackingHistory(Enviroment environment, Equipment equipment, EnumTrackingAction action, EnumColors warning) {
        this.idEquipment = equipment;
        this.id_environment = environment;
        this.action = action;
        this.warning = warning;
    }
}
