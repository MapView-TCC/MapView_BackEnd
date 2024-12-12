package com.MapView.BackEnd.dtos.Responsible;

import com.MapView.BackEnd.entities.Classes;
import com.MapView.BackEnd.entities.Responsible;
import com.MapView.BackEnd.entities.Users;

public record ResponsibleDetailsDTO(Long responsible_id, String responsible, String edv, Classes classes, Users users) {
    public ResponsibleDetailsDTO(Responsible responsible) {
        this(responsible.getId_responsible(), responsible.getResponsible(), responsible.getEdv(), responsible.getClasses(), responsible.getUser());
    }
}
