package com.MapView.BackEnd.entities;

import jakarta.persistence.*;
import lombok.*;

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
    private Equipment id_equipment;
    private Enviroment id_enviroment;
    private boolean operative;
}
