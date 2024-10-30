package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.CostCenter.CostCenterCreateDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "cost_center")
@Entity(name = "cost_center")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_cost_center")

public class CostCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cost_center;
    @Column(name = "cost_center_name")
    private String costCenter;

    private boolean operative;

    public CostCenter(CostCenterCreateDTO dados) {
        this.costCenter = dados.costCenter();
        this.operative = true;
    }

    public boolean status_check(){
        return this.operative;
    }
}

