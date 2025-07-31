package dev.scastillo.ecommerce.user.application.service;

import dev.scastillo.ecommerce.shared.utils.PasswordUtil;
import dev.scastillo.ecommerce.user.domain.model.User;
import dev.scastillo.ecommerce.user.domain.repository.UserRepository;
import dev.scastillo.ecommerce.user.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(User newUser, String password) {
        newUser.setPasswordHash(passwordUtil.hash(password));
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(UUID id, User user) {
        User existingUser = userRepository.findById(id);

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setStatus(user.getStatus());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
