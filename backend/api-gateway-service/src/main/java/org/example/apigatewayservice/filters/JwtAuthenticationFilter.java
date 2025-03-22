package org.example.apigatewayservice.filters;

import lombok.extern.slf4j.Slf4j;
import org.example.apigatewayservice.services.AuthServiceClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final AuthServiceClient authServiceClient;

    public JwtAuthenticationFilter(@Lazy AuthServiceClient authServiceClient) {
        super(Config.class);
        this.authServiceClient = authServiceClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            return authServiceClient.validateToken(token)
                    .map(userDetails -> {
                        request.mutate().header(HttpHeaders.AUTHORIZATION, "X-User-Id", userDetails.getUserId());
                        log.atDebug().log("Success Authorization! " + userDetails);
                        return exchange;
                    })
                    .flatMap(chain::filter)
                    .onErrorResume(e -> {
                        log.atError().log("Failure Authorization! " + e);
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    });

//            try {
//                ResponseEntity<UserDetailsDto> response = authServiceClient.validateToken(token);
//                exchange.getRequest().mutate()
//                        .header("X-User-Id", response.getBody().getUserId())
//                        .build();
//            } catch (FeignException e) {
//                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                return exchange.getResponse().setComplete();
//            }

        };
    }

    public static class Config {
    }
}