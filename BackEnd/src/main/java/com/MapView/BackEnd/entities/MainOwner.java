package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.MainOwner.MainOwnerCreateDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerDetailsDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Table(name = "main_owner")
@Entity(name = "main_owner")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id_owner")
public class MainOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_owner;

    @Column(name = "cod_owner")
    private String codOwner;
    @OneToOne
    @JoinColumn(name = "id_cost_center")
    private CostCenter costCenter;
    @JsonIgnore
    private boolean operative;

    public MainOwner(MainOwnerCreateDTO dados, CostCenter costCenter) {
        this.codOwner = dados.cod_owner();
        this.costCenter = costCenter;
        this.operative = true;
    }
    public MainOwner(  String cod_owner, CostCenter costCenter) {
        this.codOwner = cod_owner;
        this.costCenter = costCenter;
        this.operative = true;
    }

}
