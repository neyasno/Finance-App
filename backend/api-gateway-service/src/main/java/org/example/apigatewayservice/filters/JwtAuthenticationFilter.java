package org.example.apigatewayservice.filters;

import lombok.extern.slf4j.Slf4j;
import org.example.apigatewayservice.UserDetailsDto;
import org.example.apigatewayservice.services.AuthServiceClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
@Slf4j
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
    private static final String USER_ID_HEADER = "X-User-Id";

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/v3/api-docs",
            "/swagger-ui.html",
            "/swagger-ui/"
    );

    private final AuthServiceClient authServiceClient;

    public JwtAuthenticationFilter(@Lazy AuthServiceClient authServiceClient) {
        super(Config.class);
        this.authServiceClient = authServiceClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String path = request.getPath().value();

            log.info("Request path: {}", path);

            if(EXCLUDED_PATHS.stream().anyMatch(path::startsWith)){
                log.info("Excluding path: {}", path);
                return chain.filter(exchange);
            }

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
               return failedAuthorizationResponse(exchange, new RuntimeException("Authorization header missing"));
            }

            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION).substring("Bearer ".length());

            return authServiceClient.validateToken(token)
                    .map(userDetails -> addUserIdHeader(exchange, userDetails))
                    .flatMap(chain::filter)
                    .onErrorResume(e -> failedAuthorizationResponse(exchange, e));
        };
    }

    private static ServerWebExchange addUserIdHeader(ServerWebExchange exchange, UserDetailsDto userDetails) {
        log.atDebug().log("Success Authorization! " + userDetails);
        exchange = exchange.mutate().request(exchange.getRequest().mutate().header(USER_ID_HEADER, userDetails.getUserId()).build()).build();

        return exchange;
    }

    private static Mono<Void> failedAuthorizationResponse(ServerWebExchange exchange, Throwable e) {
        log.atError().log("Failure Authorization! " + e);

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    public static class Config {
    }
}