package com.MapView.BackEnd.dtos.TrackingHistory;

import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.entities.Environment;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Responsible;
import com.MapView.BackEnd.entities.TrackingHistory;
import com.MapView.BackEnd.enums.EnumTrackingAction;
import com.MapView.BackEnd.enums.EnumWarnings;

import java.time.LocalDateTime;
import java.util.List;

public record TrakingResponsible(Long id_tracking,
                                 LocalDateTime datetimpppppe,
                                 Equipment equipment,
                                 Environment environment,
                                 EnumTrackingAction action,
                                 EnumWarnings warning,
                                 ResponsibleDetailsDTO responsibles) {
    public TrakingResponsible (TrackingHistory trackingHistory, Responsible responsibles){
        this(null, null, null,
               null,null,
                null, new ResponsibleDetailsDTO(responsibles));
    }
}
