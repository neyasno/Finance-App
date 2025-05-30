package org.example.apigatewayservice.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    private final List<String> allowedOrigins = List.of(
            "http://localhost:3000/**",
            "https://localhost:3000/**",
            "http://192.168.100.29:3000/**",
            "http://192.168.100.29:8080/**"
    );

    private final List<String> allowedMethods = List.of("GET", "POST", "PUT", "DELETE", "OPTIONS");
    private final List<String> allowedHeaders = List.of("*");

    @Value("${debug}")
    private Boolean isDebug;

    @Bean
    public CorsWebFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedHeaders(allowedHeaders);
        config.setAllowedMethods(allowedMethods);

        if(isDebug)
            config.setAllowedOriginPatterns(List.of("*"));
        else
            config.setAllowedOriginPatterns(allowedOrigins);

        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
