package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.Environment.EnviromentCreateDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Table(name = "enviroment")
@Entity(name = "enviroment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_enviroment")

public class Enviroment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_enviroment;
    private String enviroment_name;
    @OneToOne
    @JoinColumn(name = "id_raspberry")
    private Raspberry raspberry;
    private boolean operative;
    @ManyToMany
    @JoinTable(
            name = "location",
            joinColumns = @JoinColumn(name = "id_post"),
            inverseJoinColumns =  @JoinColumn(name = "id_enviroment"))
    @JsonManagedReference
    private Set<Post> environments;


    public Enviroment(EnviromentCreateDTO data,Raspberry raspberry){
        this.enviroment_name = data.environment_name();
        this.raspberry = raspberry;
        this.operative = true;
    }
}
