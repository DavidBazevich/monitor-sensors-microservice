package org.senla.service.Impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.senla.dto.AuthRequest;
import org.senla.dto.AuthResponse;
import org.senla.dto.RegisterRequest;

import java.io.IOException;

public interface AuthServiceImpl {
    AuthResponse register(RegisterRequest request);
    AuthResponse authenticate(AuthRequest request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
