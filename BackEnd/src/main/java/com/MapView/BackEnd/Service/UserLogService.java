package com.MapView.BackEnd.Service;

import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import com.MapView.BackEnd.dtos.UserLog.UserLogDetailDTO;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.EnumAction;

import java.time.Instant;
import java.util.List;

public interface UserLogService {
    UserLogDetailDTO getUserLog(Long id_user);
    List<UserLogDetailDTO> getAllUserLog();
    UserDetailsDTO createUserLog(Users user, String altered_table, String id_altered, String field, String description, Instant datetime, EnumAction action);

}
