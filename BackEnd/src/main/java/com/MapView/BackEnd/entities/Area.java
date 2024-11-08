package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.Area.AreaCreateDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "area")
@Entity(name = "area")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_area")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_area;
    @Schema(description = "Area code", example = "CA600", required = true)
    @Column(name = "ara_code")
    private String code;
    @Schema(description = "Area name", example = "Área A", required = true)
    private String area_name;
    @Schema(description = "Operative", example = "True or False")
    @JsonIgnore
    private boolean operative;

    public Area(AreaCreateDTO dados) {
        this.code = dados.area_code();
        this.area_name = dados.area_name();
        this.operative = true;
    }}
