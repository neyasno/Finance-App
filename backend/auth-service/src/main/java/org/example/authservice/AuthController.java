package org.example.authservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @GetMapping("/validate")
    public ResponseEntity<UserDetailsDto> validateToken(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");

        if (!jwtService.validateToken(jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String userId = jwtService.extractUserId(jwt);
        UserDetailsDto userDetailsDto = new UserDetailsDto(userId);

        return ResponseEntity.ok(userDetailsDto);
    }

    @PostMapping("/token")
    public ResponseEntity<String> createToken(@RequestBody UserDetailsDto userDetailsDto) {
        String token = jwtService.generateToken(userDetailsDto.getUserId());

        return ResponseEntity.ok(token);
    }
}


