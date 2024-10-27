package com.MapView.BackEnd.tests;

import com.MapView.BackEnd.dtos.User.UserCreateDTO;
import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.RoleUser;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.serviceImp.UserServiceImp;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Usar o banco H2 real
@Transactional
public class UserTest {

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

        // Mock the repository's save method
        when(userRepository.save(any(Users.class))).thenReturn(savedUser);

        // Act
        UserDetailsDTO result = userServiceImp.createUser(userCreateDTO);

        System.out.println(result);

        // Assert
        assertNotNull(result);
        assertEquals("test@gmail.com", result.email());
        assertEquals(RoleUser.USER, result.roleUser());
    }
}
