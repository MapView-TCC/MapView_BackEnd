package com.MapView.BackEnd.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "environment")
@Entity(name = "environment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_environment")

public class Enviroment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_environment;
    private String environment_name;
    @OneToOne
    @JoinColumn(name = "id_raspberry")
    private Raspberry id_raspberry;
    private boolean operative;
}
