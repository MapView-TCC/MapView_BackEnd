package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.UserLog.UserLogCreateDTO;
import com.MapView.BackEnd.dtos.UserLog.UserLogDetailsDTO;
import com.MapView.BackEnd.entities.UserLog;

import java.util.List;

public interface UserLogService {
    UserLogDetailsDTO getUserLog(Long userLog_id);
    List<UserLogDetailsDTO> getAllUserLog();
    UserLogDetailsDTO createUserLog(UserLogCreateDTO data);

}
