package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.CostCenter.CostCenterDTO;
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
    private String cost_center_name;
    private boolean operative;

    public CostCenter(String cost_center_name, Long id_cost_center) {
        this.cost_center_name = cost_center_name;
        this.id_cost_center = id_cost_center;
    }

    public CostCenter(CostCenterDTO dados) {
    }
}

