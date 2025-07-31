package dev.scastillo.ecommerce.unit.user.application.service;

import dev.scastillo.ecommerce.shared.utils.PasswordUtil;
import dev.scastillo.ecommerce.user.application.service.UserServiceImpl;
import dev.scastillo.ecommerce.user.domain.model.User;
import dev.scastillo.ecommerce.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class UserServiceImplTest {

    private UserRepository userRepository;
    private PasswordUtil passwordUtil;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordUtil = mock(PasswordUtil.class);
        userService = new UserServiceImpl(userRepository, passwordUtil);
    }

    @Test
    void shouldHashPasswordAndSaveUser() {
        User user = new User();
        String rawPassword = "123456";
        String hashedPassword = "hashed123";

        when(passwordUtil.hash(rawPassword)).thenReturn(hashedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.createUser(user, rawPassword);

        assertEquals(hashedPassword, result.getPasswordHash());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertEquals(hashedPassword, userCaptor.getValue().getPasswordHash());

        verify(passwordUtil).hash(rawPassword);
    }

    @Test
    void shouldReturnAllUsers() {
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(users, result);
        verify(userRepository).findAll();
    }

    @Test
    void shouldReturnUserById() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(user);

        User result = userService.getUserById(userId);

        assertEquals(user, result);
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldUpdateUserFieldsAndSave() {
        UUID userId = UUID.randomUUID();
        User existingUser = new User();
        existingUser.setFirstName("Old");
        existingUser.setLastName("Name");
        existingUser.setEmail("old@email.com");
        existingUser.setStatus(false);

        User updateData = new User();
        updateData.setFirstName("New");
        updateData.setLastName("User");
        updateData.setEmail("new@email.com");
        updateData.setStatus(true);

        when(userRepository.findById(userId)).thenReturn(existingUser);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.updateUser(userId, updateData);

        assertEquals("New", result.getFirstName());
        assertEquals("User", result.getLastName());
        assertEquals("new@email.com", result.getEmail());
        assertEquals(true, result.getStatus());

        verify(userRepository).findById(userId);
        verify(userRepository).save(existingUser);
    }

    @Test
    void shouldDeleteUserById() {
        UUID userId = UUID.randomUUID();

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }
}
