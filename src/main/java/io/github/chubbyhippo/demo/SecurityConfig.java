package io.github.chubbyhippo.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());
        http.authorizeHttpRequests(registry ->
                registry.anyRequest()
                        // allow only after 12:00 pm
                        .access(new WebExpressionAuthorizationManager("""
                                T(java.time.LocalTime).now().isAfter(T(java.time.LocalTime).of(12, 0))
                                """))
        );
        return http.build();
    }
}
