package org.example.userservice.services;

import lombok.RequiredArgsConstructor;
import org.example.userservice.models.User;
import org.example.userservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(Exception::new);
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User updateUserPassword(Long id, String password) {

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        user.setPassword(password);
        return userRepository.save(user);
    }
}
