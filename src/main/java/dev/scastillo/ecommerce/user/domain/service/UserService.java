package dev.scastillo.ecommerce.user.domain.service;

import dev.scastillo.ecommerce.user.domain.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(UUID id);
    User createUser(User newUser, String password);
    User updateUser(UUID id, User user);
    void deleteUser(UUID id);
}
