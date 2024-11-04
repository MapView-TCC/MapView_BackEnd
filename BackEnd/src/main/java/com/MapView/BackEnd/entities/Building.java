package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.Building.BuildingCreateDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private boolean operative;

    public Building(BuildingCreateDTO dados) {
        this.building_code = dados.building_code();
        this.operative = true;
    }


    public boolean status_check(){
        return this.operative;
    }
}
