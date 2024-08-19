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
    @OneToOne
    @JoinColumn(name = "id_classes")
    private Classes id_classes;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private Users id_user;
    private boolean operative;
}
