package dev.scastillo.ecommerce.unit.shared.utils;

import dev.scastillo.ecommerce.shared.utils.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PasswordUtilTest {

    private PasswordEncoder passwordEncoder;
    private PasswordUtil passwordUtil;

    @BeforeEach
    void setUp() {
        passwordEncoder = mock(PasswordEncoder.class);
        passwordUtil = new PasswordUtil(passwordEncoder);
    }

    @Test
    void shouldHashPassword() {
        String rawPassword = "mySecret";
        String expectedHash = "hashedPassword";
        when(passwordEncoder.encode(rawPassword)).thenReturn(expectedHash);

        String result = passwordUtil.hash(rawPassword);

        assertEquals(expectedHash, result);
        verify(passwordEncoder).encode(rawPassword);
    }

    @Test
    void shouldMatchPassword() {
        String rawPassword = "mySecret";
        String hash = "hashedPassword";
        when(passwordEncoder.matches(rawPassword, hash)).thenReturn(true);

        boolean result = passwordUtil.matches(rawPassword, hash);

        assertEquals(true, result);
        verify(passwordEncoder).matches(rawPassword, hash);
    }
}
