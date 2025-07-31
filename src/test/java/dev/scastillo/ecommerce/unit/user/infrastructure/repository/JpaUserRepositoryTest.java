package dev.scastillo.ecommerce.unit.user.infrastructure.repository;

import dev.scastillo.ecommerce.user.domain.model.User;
import dev.scastillo.ecommerce.user.infrastructure.repository.JpaUserRepository;
import dev.scastillo.ecommerce.user.infrastructure.repository.SpringDataUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JpaUserRepositoryTest {

    private SpringDataUserRepository springDataUserRepository;
    private JpaUserRepository jpaUserRepository;

    @BeforeEach
    void setUp() {
        springDataUserRepository = mock(SpringDataUserRepository.class);
        jpaUserRepository = new JpaUserRepository(springDataUserRepository);
    }

    @Test
    void shouldReturnAllUsers() {
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);

        when(springDataUserRepository.findAll()).thenReturn(users);

        List<User> result = jpaUserRepository.findAll();

        assertEquals(users, result);
        verify(springDataUserRepository).findAll();
    }

    @Test
    void shouldReturnUserByIdWhenExists() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        when(springDataUserRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = jpaUserRepository.findById(userId);

        assertEquals(user, result);
        verify(springDataUserRepository).findById(userId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundById() {
        UUID userId = UUID.randomUUID();
        when(springDataUserRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            jpaUserRepository.findById(userId);
        });

        assertTrue(exception.getMessage().contains("User not found with id: " + userId));
        verify(springDataUserRepository).findById(userId);
    }

    @Test
    void shouldSaveUser() {
        User user = new User();
        when(springDataUserRepository.save(user)).thenReturn(user);

        User result = jpaUserRepository.save(user);

        assertEquals(user, result);
        verify(springDataUserRepository).save(user);
    }

    @Test
    void shouldDeleteUserById() {
        UUID userId = UUID.randomUUID();

        jpaUserRepository.deleteById(userId);

        verify(springDataUserRepository).deleteById(userId);
    }

    @Test
    void shouldReturnUserByEmailWhenExists() {
        String email = "test@example.com";
        User user = new User();
        when(springDataUserRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User result = jpaUserRepository.findByEmail(email);

        assertEquals(user, result);
        verify(springDataUserRepository).findByEmail(email);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundByEmail() {
        String email = "notfound@example.com";
        when(springDataUserRepository.findByEmail(email)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            jpaUserRepository.findByEmail(email);
        });

        assertTrue(exception.getMessage().contains("User not found with email: " + email));
        verify(springDataUserRepository).findByEmail(email);
    }
}
