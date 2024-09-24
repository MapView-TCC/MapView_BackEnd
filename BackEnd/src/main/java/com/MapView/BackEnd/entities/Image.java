package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.enums.EnumModelEquipment;
import jakarta.persistence.*;
import lombok.*;

import java.nio.file.Path;

@Entity(name = "image")
@Table(name = "image")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_image")


public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_image;
    private String image;
    @Enumerated(EnumType.STRING)
    private EnumModelEquipment model;

    public Image(String targetLocation, EnumModelEquipment equipmentModel) {
        this.image  = targetLocation;
        this.model = equipmentModel;

    }
}
