package com.MapView.BackEnd.dtos.MainOwner;

import com.MapView.BackEnd.entities.CostCenter;
import com.MapView.BackEnd.entities.MainOwner;

public record MainOwnerDetailsDTO(
        Long id_owner,
        String cod_owner,
        CostCenter costCenter
) {

    public MainOwnerDetailsDTO(MainOwner mainOwner) {
        this(mainOwner.getId_owner(), mainOwner.getCod_owner(), mainOwner.getCostCenter());
    }
}
