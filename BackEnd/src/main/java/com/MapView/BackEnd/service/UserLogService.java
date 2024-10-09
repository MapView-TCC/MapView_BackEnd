package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import com.MapView.BackEnd.dtos.UserLog.UserLogDetailDTO;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.EnumAction;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

public interface UserLogService {
    UserLogDetailDTO getUserLog(Long userLog_id);
    List<UserLogDetailDTO> getAllUserLog();
    Void createUserLog(Long user_id, UserLog userLog);

}
