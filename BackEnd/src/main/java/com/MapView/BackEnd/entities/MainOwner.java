package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.MainOwner.MainOwnerCreateDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerDetailsDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "main_owner")
@Entity(name = "main_owner")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id_owner")
public class MainOwner {
    @Id
    private String id_owner;
    private String owner_name;
    @OneToOne
    @JoinColumn(name = "id_cost_center")
    private CostCenter id_cost_center;
    private boolean operative;


    public MainOwner(MainOwnerCreateDTO dados, CostCenter costCenter) {
        this.id_owner = dados.id_owner();
        this.owner_name =  dados.owner_name();
        this.id_cost_center = costCenter;
        this.operative = true;
    }

}
