package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.AccessHistory.AccessHistoryCreateDTO;
import com.MapView.BackEnd.dtos.AccessHistory.AccessHistoryDetailsDTO;
import com.MapView.BackEnd.entities.AccessHistory;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.RoleUser;
import com.MapView.BackEnd.repository.AccessHistoryRepository;
import com.MapView.BackEnd.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test") // configurar o banco de dados H2
@Transactional
public class AccessHistoryServiceImpTest {

    @InjectMocks
    private AccessHistoryServiceImp accessHistoryServiceImp;

    @Mock
    private AccessHistoryRepository accessHistoryRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void getAccessHistory() {
        Long historyId = 1L;
        Long userId = 1L;

        Users users = new Users();
        users.setId_user(userId);
        users.setEmail("email@email.com");
        users.setRole(RoleUser.USER);
        users.setOperative(true);
        when(userRepository.findById(users.getId_user())).thenReturn(Optional.of(users));

        AccessHistory accessHistory = new AccessHistory();
        accessHistory.setId_history(historyId);
        accessHistory.setLogin_datetime(LocalDateTime.now());
        accessHistory.setLogout_datetime(LocalDateTime.now().plusHours(3));
        accessHistory.setUser(users);
        when(accessHistoryRepository.findById(historyId)).thenReturn(Optional.of(accessHistory));

        AccessHistoryDetailsDTO result = accessHistoryServiceImp.getAccessHistory(historyId);
        System.out.println(result);

        assertNotNull(result);
    }

    @Test
    void getAllAccessHistory() {
        Long historyId = 1L;
        Long userId = 1L;

        Users users = new Users();
        users.setId_user(userId);
        users.setEmail("email@email.com");
        users.setRole(RoleUser.USER);
        users.setOperative(true);
        when(userRepository.findById(users.getId_user())).thenReturn(Optional.of(users));

        AccessHistory accessHistory = new AccessHistory();
        accessHistory.setId_history(historyId);
        accessHistory.setLogin_datetime(LocalDateTime.now());
        accessHistory.setLogout_datetime(LocalDateTime.now().plusHours(3));
        accessHistory.setUser(users);
        when(accessHistoryRepository.findAll()).thenReturn(Collections.singletonList(accessHistory));

        List<AccessHistoryDetailsDTO> result = accessHistoryServiceImp.getAllAccessHistory();
        System.out.println(result);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(accessHistory.getId_history(), result.get(0).id_history());
    }

    @Test
    void createAccessHistory() {
        Long historyId = 1L;
        Long userId = 1L;

        Users users = new Users();
        users.setId_user(userId);
        users.setEmail("email@email.com");
        users.setRole(RoleUser.USER);
        users.setOperative(true);
        when(userRepository.findById(users.getId_user())).thenReturn(Optional.of(users));

        AccessHistoryCreateDTO createDTO = new AccessHistoryCreateDTO(userId, LocalDateTime.now());

        AccessHistory accessHistory = new AccessHistory();
        accessHistory.setId_history(historyId);
        accessHistory.setLogin_datetime(LocalDateTime.now());
        accessHistory.setLogout_datetime(LocalDateTime.now().plusHours(3));
        accessHistory.setUser(users);
        when(accessHistoryRepository.findById(historyId)).thenReturn(Optional.of(accessHistory));
    }

    @Test
    void updateAccessHistory() {
        Long historyId = 1L;

        AccessHistory accessHistory = new AccessHistory();
        accessHistory.setId_history(historyId);
        accessHistory.setLogin_datetime(LocalDateTime.now());

        when(accessHistoryRepository.findById(historyId)).thenReturn(Optional.of(accessHistory));

        AccessHistoryDetailsDTO result = accessHistoryServiceImp.updateAccessHistory(historyId);

        // Verificando se a data de logout foi atualizada
        assertNotNull(result);
        assertNotNull(result.logout_dateTime());
        assertEquals(historyId, result.id_history());

        // salvo no repositorio
        verify(accessHistoryRepository).save(accessHistory);
        assertEquals(accessHistory.getLogout_datetime(), result.logout_dateTime());
    }
}
