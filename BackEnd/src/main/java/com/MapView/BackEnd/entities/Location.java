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
    @OneToMany
    @JoinColumn(name = "id_post")
    private List<Post> id_post;

    @OneToMany
    @JoinColumn(name = "id_enviroment")
    private List<Enviroment> id_enviroment;

}
