package com.MapView.BackEnd.dtos.MainOwner;

import com.MapView.BackEnd.entities.CostCenter;
import com.MapView.BackEnd.entities.MainOwner;

public record MainOwnerDetailsDTO(
        String id_owner,
        String owner_name,
        CostCenter costCenter
) {

    public MainOwnerDetailsDTO(MainOwner mainOwner) {
        this(mainOwner.getId_owner(), mainOwner.getOwner_name(), mainOwner.getCostCenter());
    }
}
