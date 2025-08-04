package dev.scastillo.ecommerce.user.adapter.web.controller;

import dev.scastillo.ecommerce.shared.auth.PublicEndpoint;
import dev.scastillo.ecommerce.user.adapter.web.dto.LoginRequestDto;
import dev.scastillo.ecommerce.user.adapter.web.dto.LoginResponseDto;
import dev.scastillo.ecommerce.user.adapter.web.mapper.LoginMapper;
import dev.scastillo.ecommerce.user.domain.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final LoginMapper loginMapper;

    @PostMapping("/login")
    @PublicEndpoint
    public LoginResponseDto login(@RequestBody LoginRequestDto request){
        return loginMapper.toLoginResponseDto(authService.login(request.getEmail(), request.getPassword()));
    }

    /* @GetMapping("/profile")
    public ResponseEntity<?> profile(@AuthenticatedUserId UUID userId) {
        // userId viene del token validado
        return ResponseEntity.ok(Map.of("userId", userId));
    }*/

}
