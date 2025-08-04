package dev.scastillo.ecommerce.shared.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenValidator tokenValidator;

    public AuthenticationInterceptor(TokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Solo valida si NO está marcado como público
        if (handler instanceof HandlerMethod handlerMethod) {
            boolean isPublic = handlerMethod.hasMethodAnnotation(PublicEndpoint.class)
                    || handlerMethod.getBeanType().isAnnotationPresent(PublicEndpoint.class);

            if (isPublic) {
                return true; // Permitir acceso sin autenticación
            }

            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": {\"message\": \"No autorizado\"}}");
                return false;
            }
            String token = authHeader.substring("Bearer ".length());
            UUID userId = tokenValidator.validateAndGetUserId(token);
            if (userId == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": {\"message\": \"No autorizado\"}}");
                return false;
            }
            // Guarda el userId en el request para que pueda inyectarse después
            request.setAttribute("userId", userId);
        }
        return true;
    }
}
