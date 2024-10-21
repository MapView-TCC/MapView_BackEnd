package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.enums.EnumColors;
import com.MapView.BackEnd.enums.EnumTrackingAction;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Table(name = "tracking_history")
@Entity(name = "tracking_history")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id_tracking")
public class TrackingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tracking")
    private Long id;

    @CreationTimestamp
    // Use aspas invertidas para forçar o nome exato
    //@Column(updatable = false) // Se você não quiser que a data de criação seja atualizada
    private Instant datetime;

    @OneToOne
    @JoinColumn(name ="id_equipment")
    private Equipment equipment;

    @OneToOne
    @JoinColumn(name ="id_environment")
    private Environment environment;

    private Long rfid;

    @Enumerated(value = EnumType.STRING)
    private EnumTrackingAction action;

    @Enumerated(value = EnumType.STRING)
    private EnumColors warning;



    public TrackingHistory(Long rfid, Environment environment, EnumColors warning) {
        this.equipment = null;
        this.rfid = rfid;
        this.environment = environment;
        this.action = null;
        this.warning = warning;
    }

    public TrackingHistory(Environment environment, Equipment equipment, Long rfid, EnumTrackingAction action, EnumColors warning) {
        this.equipment = equipment;
        this.rfid = rfid;
        this.environment = environment;
        this.action = action;
        this.warning = warning;
    }

    // para salvar um tracking history quando salvar um equipment
    public TrackingHistory(Equipment equipment, Environment environment, Long rfid, EnumTrackingAction action, EnumColors warning) {
        this.datetime = Instant.now();
        this.equipment = equipment;
        this.environment = environment;
        this.rfid = rfid;
        this.action = action;
        this.warning = warning;
    }

}
