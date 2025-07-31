package dev.scastillo.ecommerce.user.infrastructure.repository;

import dev.scastillo.ecommerce.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
