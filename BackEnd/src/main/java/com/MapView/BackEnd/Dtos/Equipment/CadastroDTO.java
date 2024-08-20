package com.MapView.BackEnd.Dtos.Equipment;

import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.MainOwner;

public record CadastroDTO(
        String id_equipment,
        String rfid,
        String type,
        String model,
        String admin_rights,
        String observation,
        Long id_location,
        Long id_owner
) {
}
