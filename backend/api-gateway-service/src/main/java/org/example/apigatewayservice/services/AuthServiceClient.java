package org.example.apigatewayservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.apigatewayservice.UserDetailsDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceClient {

    @Value("${spring.cloud.gateway.routes[0].uri}")
    private String authHost;
    private final WebClient webClient;

    public Mono<UserDetailsDto> validateToken(String token) {
        return webClient.get()
                .uri(authHost + "/auth/validate")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, clientResponse.logPrefix())))
                .toEntity(UserDetailsDto.class)
                .mapNotNull(HttpEntity::getBody);
    }
}


//@FeignClient(name = "auth-service", path = "/auth")
//public interface AuthServiceClient {
//
//    @GetMapping("/validate")
//    ResponseEntity<UserDetailsDto> validateToken(@RequestHeader("Authorization") String token);
//}
//
//
//@ReactiveFeignClient(name = "auth-service", path = "/auth")
//public interface AuthServiceClient {
//
//    @GetMapping("/validate")
//    Mono<ResponseEntity<UserDetailsDto>> validateToken(@RequestHeader("Authorization") String token);
//}