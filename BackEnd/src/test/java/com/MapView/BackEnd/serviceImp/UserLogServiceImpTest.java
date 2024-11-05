package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.UserLog.UserLogCreateDTO;
import com.MapView.BackEnd.dtos.UserLog.UserLogDetailsDTO;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserLogServiceImpTest {

    @InjectMocks
    private UserLogServiceImp userLogServiceImp;

    @Mock
    private UserLogRepository userLogRepository;

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
        userLog.setField("Campo do Id");
        userLog.setDatetime(LocalDateTime.now());
        userLog.setAction(EnumAction.READ);
        when(userLogRepository.findById(userLogId)).thenReturn(Optional.of(userLog));

        UserLogDetailsDTO result = userLogServiceImp.getUserLog(userLogId);
        System.out.println(result);

        assertNotNull(result);
        assertEquals(userLogId, result.id_log());
        assertEquals("Campo do Id", result.field());

    }

    @Test
    void getAllUserLog() {
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
        userLog.setField("Campo do Id");
        userLog.setDatetime(LocalDateTime.now());
        userLog.setAction(EnumAction.READ);

        Long userLogId2 = 2L;
        UserLog userLog2 = new UserLog();
        userLog2.setId_log(userLogId2);
        userLog2.setUser(user);
        userLog2.setDescription("outro teste");
        userLog2.setField("Outro Campo");
        userLog2.setDatetime(LocalDateTime.now().plusMinutes(10));
        userLog2.setAction(EnumAction.UPDATE);

        when(userLogRepository.findAll()).thenReturn(Arrays.asList(userLog, userLog2));

        List<UserLogDetailsDTO> result = userLogServiceImp.getAllUserLog();
        System.out.println(result);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("teste", result.get(0).description());
        assertEquals("outro teste", result.get(1).description());
    }

    @Test
    void createUserLog() {
        Long userLogId = 1L;
        Long userId = 1L;

        // Mock da entidade Users
        Users user = new Users();
        user.setId_user(userId);
        user.setOperative(true);
        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));

        UserLogCreateDTO createDTO = new UserLogCreateDTO(userId, "Campo", "1", "Campo do id", "teste", EnumAction.CREATE);

        UserLog userLog = new UserLog();
        userLog.setId_log(userLogId);
        userLog.setUser(user);
        userLog.setAltered_table(createDTO.altered_table());
        userLog.setId_altered(createDTO.id_altered());
        userLog.setDescription(createDTO.description());
        userLog.setField(createDTO.field());
        userLog.setDatetime(LocalDateTime.now());
        userLog.setAction(createDTO.action());
        when(userLogRepository.save(any(UserLog.class))).thenReturn(userLog);

        UserLogDetailsDTO result = userLogServiceImp.createUserLog(createDTO);
        System.out.println(result);

        assertNotNull(result);
        assertEquals("1", result.id_altered());
    }

}
