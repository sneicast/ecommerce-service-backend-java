package dev.scastillo.ecommerce.user.adapter.web.controller;

import dev.scastillo.ecommerce.user.adapter.web.dto.UserCreateRequestDto;
import dev.scastillo.ecommerce.user.adapter.web.dto.UserDto;
import dev.scastillo.ecommerce.user.adapter.web.dto.UserUpdateRequestDto;
import dev.scastillo.ecommerce.user.adapter.web.mapper.UserMapper;
import dev.scastillo.ecommerce.user.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public void createUser(@RequestBody UserCreateRequestDto request) {
        userService.createUser(userMapper.toDomainCreate(request), request.getPassword());
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(userMapper::toDto)
                .toList();
    }
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable UUID id) {
        return userMapper.toDto(userService.getUserById(id));
    }
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable UUID id, @RequestBody UserUpdateRequestDto request) {
        return userMapper.toDto(userService.updateUser(id, userMapper.toDomainUpdate(request)));
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }
}
