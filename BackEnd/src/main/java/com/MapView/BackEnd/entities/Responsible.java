package com.MapView.BackEnd.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "responsible")
@Entity(name = "responsible")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_responsible")

public class Responsible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_responsible;

    private String responsible_name;
    private String edv;
    private Classes id_classes;
    private User id_user;
    private boolean operative;
}
