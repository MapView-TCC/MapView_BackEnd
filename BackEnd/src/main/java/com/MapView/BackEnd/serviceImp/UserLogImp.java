package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.service.UserLogService;
import com.MapView.BackEnd.dtos.UserLog.UserLogDetailDTO;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
    public UserLogDetailDTO getUserLog(Long id_user) {
        UserLog userLog = this.userLogRepository.findById(id_user).orElseThrow(() -> new NotFoundException("User Id not found"));

        return new UserLogDetailDTO(userLog);
    }

    @Override
    public List<UserLogDetailDTO> getAllUserLog(int page, int itens) {
        return this.userLogRepository.findAll(PageRequest.of(page,itens)).stream().map(UserLogDetailDTO::new).toList();
    }


    @Override
    public Void createUserLog(Long user_id, UserLog userLog) {
        var user = userRepository.findById(user_id).orElseThrow(()-> new NotFoundException("User Id not Found"));
        userLogRepository.save(userLog);
        return null;
    }
}
