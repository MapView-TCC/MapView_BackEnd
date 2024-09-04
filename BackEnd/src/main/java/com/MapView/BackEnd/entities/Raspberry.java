package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.Raspberry.RaspberryCreateDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "raspberry")
@Entity(name = "raspberry")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "raspberry_name")
public class Raspberry {
    @Id
    private String raspberry_name;
    @OneToOne
    @JoinColumn(name = "id_building")
    private Building id_building;
    @OneToOne
    @JoinColumn(name = "id_area")
    private Area id_area;
    private boolean operative;


    public Raspberry(RaspberryCreateDTO dados, Building id_building, Area id_area) {
        this.raspberry_name = dados.raspberry_name();
        this.id_building = id_building;
        this.id_area = id_area;
        this.operative = true;
    }


}

