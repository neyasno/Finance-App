package org.example.authservice.repositories;

import org.example.authservice.models.RecoverPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecoverPasswordTokenRepository extends JpaRepository<RecoverPasswordToken, String> {
    Optional<RecoverPasswordToken> findByToken(String token);

    void deleteByToken(String token);
}