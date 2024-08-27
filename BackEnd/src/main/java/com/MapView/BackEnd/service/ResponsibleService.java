package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Responsible.ResponsibleCrateDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleUpdateDTO;

import java.util.List;

public interface ResponsibleService {
    ResponsibleDetailsDTO getResposible(Long id_Resposible);
    List<ResponsibleDetailsDTO> getAllResposible();
    ResponsibleDetailsDTO createResposible(ResponsibleCrateDTO data);
    ResponsibleDetailsDTO updateResposible(Long id_responsible, ResponsibleUpdateDTO data);

    void activeResposible(Long id_Resposible);
    void inactivateEnviroment(Long id_Resposible);
}
