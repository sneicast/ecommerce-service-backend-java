package dev.scastillo.ecommerce.shared.auth;

import java.util.UUID;

public interface TokenValidator {
    UUID validateAndGetUserId(String token);
}
