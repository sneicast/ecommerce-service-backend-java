package dev.scastillo.ecommerce.user.domain.repository;

import dev.scastillo.ecommerce.user.domain.model.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    List<User> findAll();
    User findById(UUID id);
    User save(User user);
    void deleteById(UUID id);
    User findByEmail(String email);
}
