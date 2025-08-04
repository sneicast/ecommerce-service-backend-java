package dev.scastillo.ecommerce.shared.auth;

import dev.scastillo.ecommerce.shared.utils.JwtUtil;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtTokenValidator implements TokenValidator {

    private final JwtUtil jwtUtil;

    public JwtTokenValidator(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UUID validateAndGetUserId(String token) {
        return jwtUtil.validateAndGetUserId(token);
    }
}
