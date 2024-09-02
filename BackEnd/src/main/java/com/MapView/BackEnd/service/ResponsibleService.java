package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Responsible.ResponsibleCrateDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleUpdateDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ResponsibleService {
    ResponsibleDetailsDTO getResposible(Long id_Resposible, Long user_id);
    List<ResponsibleDetailsDTO> getAllResposible(int page, int itens, Long user_id);
    ResponsibleDetailsDTO createResposible(ResponsibleCrateDTO data, Long user_id);
    ResponsibleDetailsDTO updateResposible(Long id_responsible, ResponsibleUpdateDTO data, Long user_id);

    void activeResposible(Long id_Resposible, Long user_id);
    void inactivateResposible(Long id_Resposible, Long user_id);
}
