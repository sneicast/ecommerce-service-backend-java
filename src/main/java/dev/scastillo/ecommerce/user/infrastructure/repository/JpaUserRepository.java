package dev.scastillo.ecommerce.user.infrastructure.repository;

import dev.scastillo.ecommerce.user.domain.model.User;
import dev.scastillo.ecommerce.user.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaUserRepository implements UserRepository {
    private final SpringDataUserRepository repository;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id));
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
