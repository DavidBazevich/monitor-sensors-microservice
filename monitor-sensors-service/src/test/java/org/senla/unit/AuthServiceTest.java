package org.senla.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.senla.dto.AuthRequest;
import org.senla.dto.AuthResponse;
import org.senla.dto.RegisterRequest;
import org.senla.entity.Role;
import org.senla.entity.User;
import org.senla.filter.JwtService;
import org.senla.repository.TokenRepository;
import org.senla.repository.UserRepository;
import org.senla.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ServletOutputStream outputStream;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private AuthRequest authRequest;
    private RegisterRequest registerRequest;
    private User user;

    @BeforeEach
    void setUp(){
        user = User.builder()
                .name("david")
                .password("encodedPassword")
                .role(Role.VIEWER)
                .build();

        registerRequest = new RegisterRequest("david",
                "password",
                Role.VIEWER);

        authRequest = new AuthRequest("david", "password");
    }

    @Test
    void registerTest(){
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(user)).thenReturn("refreshToken");

        AuthResponse authResponse = authService.register(registerRequest);

        assertEquals("accessToken", authResponse.getAccessToken());
        assertEquals("refreshToken", authResponse.getRefreshToken());
        verify(userRepository, times(1)).save(any(User.class));
        verify(jwtService, times(1)).generateToken(user);
        verify(jwtService, times(1)).generateRefreshToken(user);
    }

    @Test
    void authenticateTest(){
        when(userRepository.findByName("david")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(user)).thenReturn("refreshToken");

        AuthResponse authResponse = authService.authenticate(authRequest);

        assertEquals("accessToken", authResponse.getAccessToken());
        assertEquals("refreshToken", authResponse.getRefreshToken());
    }

    @Test
    void refreshTokenTest() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer refreshToken");
        when(jwtService.extractUsername("refreshToken")).thenReturn("david");
        when(userRepository.findByName("david")).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid("refreshToken", "david")).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("newAccessToken");
        when(response.getOutputStream()).thenReturn(outputStream);

        authService.refreshToken(request, response);

        verify(jwtService, times(1)).extractUsername("refreshToken");
        verify(userRepository, times(1)).findByName("david");
        verify(jwtService, times(1)).isTokenValid("refreshToken", "david");
        verify(jwtService, times(1)).generateToken(user);
        verify(objectMapper).writeValue(eq(outputStream), any());
    }


}
