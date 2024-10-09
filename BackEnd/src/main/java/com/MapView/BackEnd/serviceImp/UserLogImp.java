package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.service.UserLogService;
import com.MapView.BackEnd.dtos.UserLog.UserLogDetailDTO;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class UserLogImp implements UserLogService {


    private final UserLogRepository userLogRepository;
    private final UserRepository userRepository;

    public UserLogImp(UserLogRepository userLogRepository, UserRepository userRepository) {
        this.userLogRepository = userLogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserLogDetailDTO getUserLog(Long userLog_id) {
        UserLog userLog = this.userLogRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("User Id not found"));

        return new UserLogDetailDTO(userLog);
    }

    @Override
    public List<UserLogDetailDTO> getAllUserLog() {
        return this.userLogRepository.findAll().stream().map(UserLogDetailDTO::new).toList();
    }


    @Override
    public Void createUserLog(Long user_id, UserLog userLog) {
        userLogRepository.save(userLog);
        return null;
    }
}
