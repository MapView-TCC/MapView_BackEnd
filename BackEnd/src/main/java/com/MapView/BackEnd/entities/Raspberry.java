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
@EqualsAndHashCode()
public class Raspberry {
    @Id
    private String id_raspberry;
    @OneToOne
    @JoinColumn(name = "id_building")
    private Building building;
    @OneToOne
    @JoinColumn(name = "id_area")
    private Area area;
    private boolean operative;


    public Raspberry(RaspberryCreateDTO dados, Building id_building, Area id_area) {
        this.id_raspberry = dados.id_raspberry();
        this.building = id_building;
        this.area = id_area;
        this.operative = true;
    }


}

