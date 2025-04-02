package org.senla.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.senla.entity.User;
import org.senla.repository.UserRepository;
import org.senla.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("David");
        user.setPassword("qwerty");
    }

    @Test
    @DisplayName("Поиск юзера по имени -> Возвращает объект юзера")
    void loadUserByUsername_IfUserExists() {
        when(userRepository.findByName("David")).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername("David");

        assertEquals(user, userDetails);
    }

    @Test
    @DisplayName("Поиск юзера по имени -> Пробрасывает исключение")
    void loadUserByUsername_IfUserDoesNotExist() {
        when(userRepository.findByName("Ivan")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("Ivan");
        });
    }

}
