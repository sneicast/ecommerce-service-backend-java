package dev.scastillo.ecommerce.user.application.service;

import dev.scastillo.ecommerce.shared.exception.UnauthorizedException;
import dev.scastillo.ecommerce.shared.utils.JwtUtil;
import dev.scastillo.ecommerce.shared.utils.PasswordUtil;
import dev.scastillo.ecommerce.user.domain.repository.UserRepository;
import dev.scastillo.ecommerce.user.domain.service.AuthService;
import dev.scastillo.ecommerce.user.domain.service.dto.LoginResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(String email, String password) {
    var user = userRepository.findByEmail(email);
    if(user == null){
        throw new UnauthorizedException("usuario o contraseña incorrectos");
    }
    boolean isPasswordValid = passwordUtil.matches(password, user.getPasswordHash());
    if(!isPasswordValid){
        throw new UnauthorizedException("usuario o contraseña incorrectos");
    }
    String token = jwtUtil.generateToken(user.getId());

        return LoginResponse.builder()
                .token(token)
                .build();
    }
}
