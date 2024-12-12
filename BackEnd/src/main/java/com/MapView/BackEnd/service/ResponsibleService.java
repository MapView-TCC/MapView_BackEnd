package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Responsible.ResponsibleCrateDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleUpdateDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ResponsibleService {
    ResponsibleDetailsDTO getResposibleById(Long id_Responsible, Long userLog_id);
    ResponsibleDetailsDTO getResposibleByEdv(String edv, Long userLog_id);
    List<ResponsibleDetailsDTO> getAllResposible(Long userLog_id);
    ResponsibleDetailsDTO createResposible(ResponsibleCrateDTO data, Long userLog_id);
    ResponsibleDetailsDTO updateResposible(Long id_responsible, ResponsibleUpdateDTO data, Long userLog_id);

    void activeResposible(Long id_Responsible, Long userLog_id);
    void inactivateResposible(Long id_Responsible, Long userLog_id);
}
