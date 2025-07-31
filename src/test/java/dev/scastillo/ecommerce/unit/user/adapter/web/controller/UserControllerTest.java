package dev.scastillo.ecommerce.unit.user.adapter.web.controller;

import dev.scastillo.ecommerce.user.adapter.web.controller.UserController;
import dev.scastillo.ecommerce.user.adapter.web.dto.UserCreateRequestDto;
import dev.scastillo.ecommerce.user.adapter.web.dto.UserDto;
import dev.scastillo.ecommerce.user.adapter.web.dto.UserUpdateRequestDto;
import dev.scastillo.ecommerce.user.adapter.web.mapper.UserMapper;
import dev.scastillo.ecommerce.user.domain.model.User;
import dev.scastillo.ecommerce.user.domain.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class UserControllerTest {
    private UserService userService;
    private UserMapper userMapper;
    private UserController userController;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        userController = new UserController(userService, userMapper);
    }

    @Test
    void shouldCreateUser() {
        String password = "password123";
        UserCreateRequestDto request = UserCreateRequestDto.builder()
                .password(password)
                .build();



        User user = new User();
        when(userMapper.toDomainCreate(request)).thenReturn(user);

        userController.createUser(request);

        verify(userMapper).toDomainCreate(request);
        verify(userService).createUser(user, password);
    }

    @Test
    void shouldGetAllUsers() {
        User user = new User();
        UserDto userDto = UserDto.builder().build();
        when(userService.getAllUsers()).thenReturn(List.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        List<UserDto> result = userController.getAllUsers();

        assertEquals(1, result.size());
        assertEquals(userDto, result.get(0));
        verify(userService).getAllUsers();
        verify(userMapper).toDto(user);
    }

    @Test
    void shouldGetUserById() {
        UUID id = UUID.randomUUID();
        User user = new User();
        UserDto userDto = UserDto.builder().build();
        when(userService.getUserById(id)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userController.getUserById(id);

        assertEquals(userDto, result);
        verify(userService).getUserById(id);
        verify(userMapper).toDto(user);
    }

    @Test
    void shouldUpdateUser() {
        UUID id = UUID.randomUUID();
        UserUpdateRequestDto request = UserUpdateRequestDto.builder().build();
        User user = new User();
        User updatedUser = new User();
        UserDto userDto = UserDto.builder().build();
        when(userMapper.toDomainUpdate(request)).thenReturn(user);
        when(userService.updateUser(id, user)).thenReturn(updatedUser);
        when(userMapper.toDto(updatedUser)).thenReturn(userDto);

        UserDto result = userController.updateUser(id, request);

        assertEquals(userDto, result);
        verify(userMapper).toDomainUpdate(request);
        verify(userService).updateUser(id, user);
        verify(userMapper).toDto(updatedUser);
    }

    @Test
    void shouldDeleteUser() {
        UUID id = UUID.randomUUID();

        userController.deleteUser(id);

        verify(userService).deleteUser(id);
    }
}
