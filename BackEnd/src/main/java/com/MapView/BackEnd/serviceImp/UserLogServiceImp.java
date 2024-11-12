package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.UserLog.UserLogCreateDTO;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.service.UserLogService;
import com.MapView.BackEnd.dtos.UserLog.UserLogDetailsDTO;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class UserLogServiceImp implements UserLogService {


    private final UserLogRepository userLogRepository;
    private final UserRepository userRepository;

    public UserLogServiceImp(UserLogRepository userLogRepository, UserRepository userRepository) {
        this.userLogRepository = userLogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserLogDetailsDTO getUserLog(Long userLog_id) {
        UserLog userLog = this.userLogRepository.findById(userLog_id)
                .orElseThrow(() -> new NotFoundException("User Log with ID " + userLog_id + " not found"));

        return new UserLogDetailsDTO(userLog);
    }

    @Override
    public List<UserLogDetailsDTO> getAllUserLog() {
        return this.userLogRepository.findAll().stream().map(UserLogDetailsDTO::new).toList();
    }


    @Override
    public UserLogDetailsDTO createUserLog(UserLogCreateDTO data) {
        try {
            Users user = userRepository.findById(data.user_id())
                    .orElseThrow(() -> new NotFoundException("User with ID " + data.user_id() + " not found"));

            UserLog userLog = new UserLog(
                    user,
                    data.altered_table(),
                    data.id_altered(),
                    data.field(),
                    data.description(),
                    data.action());

            userLog = userLogRepository.save(userLog);

            return new UserLogDetailsDTO(userLog);
        } catch (Exception e) {
            throw new RuntimeException("Error saving the user log", e);
        }
    }
}
