package io.github.chubbyhippo.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.stereotype.Component;

@Configuration
public class SecurityConfig {
    private final TimeChecker timeChecker;

    public SecurityConfig(TimeChecker timeChecker) {
        this.timeChecker = timeChecker;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
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
