package org.example.authservice.services;

import org.example.authservice.dto.CreateUserRequest;
import org.example.authservice.dto.UpdateUserPasswordRequest;
import org.example.authservice.dto.UserCredentialsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/{id}")
    ResponseEntity<UserCredentialsDTO> getUserById(@PathVariable Long id);

    @GetMapping("/by-email/{email}")
    ResponseEntity<UserCredentialsDTO> getUserByEmail(@PathVariable String email);

    @PostMapping("/")
    ResponseEntity<UserCredentialsDTO> createUser(@RequestBody CreateUserRequest request);

    @PutMapping("/{id}")
    ResponseEntity<UserCredentialsDTO> updateUserPassword(@PathVariable Long id, @RequestBody UpdateUserPasswordRequest request);
}
