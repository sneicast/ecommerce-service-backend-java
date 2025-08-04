package dev.scastillo.ecommerce.user.domain.service;

import dev.scastillo.ecommerce.user.domain.service.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(String email, String password);
}
