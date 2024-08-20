package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.Dtos.MainOwner.MainOwnerDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "main_owner")
@Entity(name = "main_owner")
@Getter
@Setter
@AllArgsConstructor
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


    public MainOwner(MainOwnerDTO data, CostCenter id_cost_center) {
        this.id_owner = data.id_owner();
        this.owner_name = data.owner_name();
        this.id_cost_center = id_cost_center;
    }
}
