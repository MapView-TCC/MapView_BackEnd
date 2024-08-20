package com.MapView.BackEnd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "location")
@Entity(name = "location")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_location")

public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_location;

    @ManyToOne
    @MapsId("id_post")
    private Post id_post;
    @ManyToOne
    @MapsId("id_enviroment")
    private Enviroment id_enviroment;

}
