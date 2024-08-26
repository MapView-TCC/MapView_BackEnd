package com.MapView.BackEnd.ServiceImp;

import com.MapView.BackEnd.Repository.AccessHistoryRepository;
import com.MapView.BackEnd.Repository.UserRepository;
import com.MapView.BackEnd.Service.AccessHistoryService;
import com.MapView.BackEnd.dtos.AccessHistory.AccessHistoryCreateDTO;
import com.MapView.BackEnd.dtos.AccessHistory.AccessHistoryDetailsDTO;
import com.MapView.BackEnd.entities.AccessHistory;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccessHistoryServiceImp implements AccessHistoryService {


    @Autowired
    private AccessHistoryRepository accessHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AccessHistoryDetailsDTO getAccessHistory(Long id_history) {
        AccessHistory accessHistory = this.accessHistoryRepository.findById(id_history)
                .orElseThrow(() -> new NotFoundException("Id not found"));

        return new AccessHistoryDetailsDTO(accessHistory);
    }

    @Override
    public List<AccessHistoryDetailsDTO> getAllAccessHistory() {
        return accessHistoryRepository.findAll().stream().map(AccessHistoryDetailsDTO::new).toList();
    }

    @Override
    public AccessHistoryDetailsDTO createAccessHistory(AccessHistoryCreateDTO dados) {
        Users user = userRepository.findById(dados.id_users())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        AccessHistory history = new AccessHistory();
        history.setId_user(user);

        // O campo login_datetime será preenchido automaticamente
        accessHistoryRepository.save(history);

        return new AccessHistoryDetailsDTO(history);
    }

    @Override
    public AccessHistoryDetailsDTO updateAccessHistory(Long id_history) {
        var history = accessHistoryRepository.findById(id_history).orElseThrow(() -> new NotFoundException("Id not found"));

        // Atualiza a data de logout no objeto history
        history.setLogout_datetime(LocalDateTime.now());

        accessHistoryRepository.save(history);
        return new AccessHistoryDetailsDTO(history);
    }
}