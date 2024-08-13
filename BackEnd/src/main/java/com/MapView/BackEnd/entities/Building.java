package com.MapView.BackEnd.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "building")
@Entity(name = "building")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_building")

public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id_building;
    private String building_code;
    private boolean operative;
}
