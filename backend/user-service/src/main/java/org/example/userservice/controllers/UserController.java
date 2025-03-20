package org.example.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.userservice.services.UserService;
import org.example.userservice.models.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) throws Exception {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser( @RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUserPassword(@PathVariable Long id , @RequestBody String password) {
        return userService.updateUserPassword(id , password);
    }
}
