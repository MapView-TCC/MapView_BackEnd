package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.Environment.EnvironmentCreateDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Table(name = "environment")
@Entity(name = "environment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_environment")
public class Environment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_environment;
    private String environment_name;
    @OneToOne
    @JoinColumn(name = "id_raspberry")
    private Raspberry raspberry;
    @JsonIgnore
    private boolean operative;
    @ManyToMany
    @JoinTable(
            name = "location",
            joinColumns = @JoinColumn(name = "id_post"),
            inverseJoinColumns =  @JoinColumn(name = "id_environment"))
    @JsonManagedReference
    private Set<Post> posts;


    public Environment(EnvironmentCreateDTO data, Raspberry raspberry){
        this.environment_name = data.environment_name();
        this.raspberry = raspberry;
        this.operative = true;
    }
}
