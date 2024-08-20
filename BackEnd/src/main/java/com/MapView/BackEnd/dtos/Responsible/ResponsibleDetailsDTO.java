package com.MapView.BackEnd.dtos.Responsible;

import com.MapView.BackEnd.entities.Classes;
import com.MapView.BackEnd.entities.Responsible;
import com.MapView.BackEnd.entities.Users;

public record ResponsibleDetailsDTO(Long responsible_id, String responsible_name, String edv, Classes classes, Users users,Boolean operative) {
    public ResponsibleDetailsDTO(Responsible responsible) {
        this(responsible.getId_responsible(), responsible.getResponsible_name(), responsible.getEdv(), responsible.getId_classes(), responsible.getId_user(),responsible.isOperative());
    }
}
