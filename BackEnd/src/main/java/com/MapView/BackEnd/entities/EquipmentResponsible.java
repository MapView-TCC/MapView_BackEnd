package com.MapView.BackEnd.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;


@Table(name = "equipment_responsible")
@Entity(name = "equipment_responsible")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_equip_resp")
public class EquipmentResponsible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_equip_resp;
    @OneToOne
    @JoinColumn(name = "id_equipment")
    private Equipment id_equipment;
    @OneToOne
    @JoinColumn(name = "id_responsible")
    private Responsible id_responsible;
    private LocalDate start_usage;
    private LocalDate end_usage;
    private boolean operative;

}
