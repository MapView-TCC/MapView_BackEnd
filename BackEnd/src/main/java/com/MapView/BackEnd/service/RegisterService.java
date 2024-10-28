package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Register.RegisterCreateDTO;
import com.MapView.BackEnd.dtos.Register.RegisterDetailsDTO;
import com.MapView.BackEnd.dtos.Register.RegisterUpdateDTO;

public interface RegisterService {

    RegisterDetailsDTO register(RegisterCreateDTO data, Long userLog_id);
    RegisterDetailsDTO updateRegister( RegisterUpdateDTO data,String id_equipment, Long userLog_id);
}
