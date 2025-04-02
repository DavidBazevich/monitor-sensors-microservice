package org.senla.unit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.senla.entity.Token;
import org.senla.repository.TokenRepository;
import org.senla.service.LogoutService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogoutServiceTest {


    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private LogoutService logoutService;

    private static final String JWT_TOKEN = "valid.jwt.token";
    private static final String BEARER = "Bearer ";

    @Test
    void testLogout_ValidToken() {
        Token storedToken = new Token();
        storedToken.setToken(JWT_TOKEN);
        storedToken.setExpired(false);
        storedToken.setRevoked(false);

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(BEARER + JWT_TOKEN);
        when(tokenRepository.findByToken(JWT_TOKEN)).thenReturn(Optional.of(storedToken));

        logoutService.logout(request, response, authentication);

        verify(request).getHeader(HttpHeaders.AUTHORIZATION);
        verify(tokenRepository).findByToken(JWT_TOKEN);
        verify(tokenRepository).save(storedToken);

        assertTrue(storedToken.isExpired());
        assertTrue(storedToken.isRevoked());
    }

    @Test
    void testLogout_TokenNotFound() {
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(BEARER + JWT_TOKEN);
        when(tokenRepository.findByToken(JWT_TOKEN)).thenReturn(Optional.empty());

        logoutService.logout(request, response, authentication);

        verify(request, times(1)).getHeader(HttpHeaders.AUTHORIZATION);
        verify(tokenRepository, times(1)).findByToken(JWT_TOKEN);
        verify(tokenRepository, never()).save(any(Token.class));
    }

}
