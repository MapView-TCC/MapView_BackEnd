package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserLogServiceImpTest {

    @InjectMocks
    private UserLogServiceImp userLogServiceImp;

    @Mock
    private UserRepository userRepository;

    @Test
    void getUserLog() {
        Long userLogId = 1L;
        Long userId = 1L;

        // Mock da entidade Users
        Users user = new Users();
        user.setId_user(userId);
        user.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        UserLog userLog = new UserLog();
        userLog.setId_log(userLogId);
        userLog.setUser(user);
        userLog.setDescription("teste");

    }

    @Test
    void getAllUserLog() {
    }

    @Test
    void createUserLog() {
    }

}
