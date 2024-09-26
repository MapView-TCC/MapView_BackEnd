package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Equipment.EquipmentCreateDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleCreateDTO;
import com.MapView.BackEnd.dtos.Location.LocationCreateDTO;
import com.MapView.BackEnd.dtos.Register.RegisterDetailsDTO;

public interface RegisterService {

    RegisterDetailsDTO register (EquipmentCreateDTO dataEquipment, LocationCreateDTO datalocation, EquipmentResponsibleCreateDTO dataResponsible,Long userLog_id);
}
