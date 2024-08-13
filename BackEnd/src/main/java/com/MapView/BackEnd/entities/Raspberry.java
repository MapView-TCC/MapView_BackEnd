package com.MapView.BackEnd.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "raspberry")
@Entity(name = "raspberry")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_raspberry")
public class Raspberry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_raspberry;
    private String raspberry_name;
    private Building id_building;
    private Area id_area;
    private boolean operative;
}
