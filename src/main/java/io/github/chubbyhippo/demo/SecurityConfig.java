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
    SecurityFilterChain securityFilterChain(HttpSecurity http, TimeChecker timeChecker) throws Exception {
        http.httpBasic(Customizer.withDefaults());
        http.authorizeHttpRequests(registry ->
                registry.anyRequest()
                        .access(new WebExpressionAuthorizationManager("""
                                %s
                                """.formatted(timeChecker.isAfter12Pm() ? "permitAll()" : "denyAll()")))
        );
        return http.build();
    }
}
