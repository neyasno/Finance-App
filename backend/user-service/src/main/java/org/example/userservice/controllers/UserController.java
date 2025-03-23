package org.example.userservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.dto.CreateUserRequest;
import org.example.userservice.services.UserService;
import org.example.userservice.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User data = userService.getUserById(id);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        try {
            User data = userService.getUserByEmail(email);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
        User userData = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        try {
            User data = userService.createUser(userData);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserPassword(@PathVariable Long id, @RequestBody String password) {
        try {
            User data = userService.updateUserPassword(id, password);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
