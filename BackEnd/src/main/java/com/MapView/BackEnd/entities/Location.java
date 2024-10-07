package com.MapView.BackEnd.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "location", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_post","id_environment"})})
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
    @JoinColumn(name = "id_post")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "id_environment")
    private Environment environment;

//    @OneToMany(mappedBy = "location") // Mapeia a relação inversa no Equipment
//    @JsonManagedReference // the one that gets serialized normally.
//    private Set<Equipment> equipments = new HashSet<>();

    public Location(Post post, Environment environment){
        this.post = post;
        this.environment = environment;
    }
}