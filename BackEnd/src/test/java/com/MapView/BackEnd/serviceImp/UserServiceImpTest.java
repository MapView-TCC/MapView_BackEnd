package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.User.UserCreateDTO;
import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.RoleUser;
import com.MapView.BackEnd.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test") // configurar o banco de dados H2
@Transactional
public class UserServiceImpTest {

    @InjectMocks
    private UserServiceImp userServiceImp;

    @Mock
    private UserRepository userRepository;

    @Test
    public void createUser() {
        // Arrange
        UserCreateDTO userCreateDTO = new UserCreateDTO("test@gmail.com");
        Users savedUser = new Users();
        savedUser.setId_user(1L);
        savedUser.setEmail(userCreateDTO.email());
        savedUser.setRole(RoleUser.USER);
        savedUser.setOperative(true);

        // salvar o user
        when(userRepository.save(any(Users.class))).thenReturn(savedUser);

        // Act
        UserDetailsDTO result = userServiceImp.createUser(userCreateDTO);

        System.out.println(result);

        // Assert
        assertNotNull(result);
        assertEquals("test@gmail.com", result.email());
        assertEquals(RoleUser.USER, result.roleUser());
    }

    @Test
    void findAllUsers() {
        Long userId = 1L;

        Users users = new Users();
        users.setId_user(userId);
        users.setEmail("test@gmail.com");
        users.setRole(RoleUser.USER);
        users.setOperative(true);

        // configurar o comportamento para retornar o usuario
        when(userRepository.findByOperativeTrue()).thenReturn(Collections.singletonList(users));

        List<UserDetailsDTO> result = userServiceImp.getAllUser();

        // Verificações
        assertNotNull(result);
        assertThat(result).isNotEmpty();
        assertEquals(1, result.size());
        assertEquals("test@gmail.com", result.get(0).email());
    }

    @Test
    void getUsersById() {
        Long userId = 1L;

        Users users = new Users();
        users.setId_user(userId);
        users.setEmail("test@gmail.com");
        users.setRole(RoleUser.USER);
        users.setOperative(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(users));

        UserDetailsDTO result = userServiceImp.getUser(userId);
        System.out.println(result);

        // Verifcações
        assertNotNull(result);
        assertEquals("test@gmail.com", result.email());
        assertEquals(RoleUser.USER, result.roleUser());
    }

    @Test
    void setUserRole() {
        Long userId = 1L;

        Users users = new Users();
        users.setId_user(userId);
        users.setEmail("test@gmail.com");
        users.setRole(RoleUser.USER);
        users.setOperative(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(users));
        userServiceImp.setPrivilege(userId, RoleUser.ADMIN);
    }
}
