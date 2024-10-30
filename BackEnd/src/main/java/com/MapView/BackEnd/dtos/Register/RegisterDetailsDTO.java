package com.MapView.BackEnd.dtos.Register;

import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.dtos.Location.LocationDetalsDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.enums.EnumModelEquipment;

import java.time.LocalDate;
import java.util.List;

public record RegisterDetailsDTO (String id_equipment,
                                  String name_equipment,
                                  long rfid,
                                  String type,
                                  EnumModelEquipment model,
                                  String validity,
                                  String admin_rights,
                                  String observation,
                                  Location location,
                                  MainOwner owner,
                                  Long id_location,
                                  Post post,
                                  Environment environment,

                                  List<ResponsibleDetailsDTO> responsible) {

    public RegisterDetailsDTO(EquipmentDetailsDTO equipment, LocationDetalsDTO location, List<ResponsibleDetailsDTO> equipmentResponsible){
        this(equipment.code(),
                equipment.name_equipment(),
                equipment.rfid(),
                equipment.type(),
                equipment.model(),
                equipment.validity(),
                equipment.admin_rights(),
                equipment.observation(),
                equipment.location(),
                equipment.owner(),
                location.id_location(),
                location.post(),
                location.environment(),
                equipmentResponsible);

    }

}
