package io.github.chubbyhippo.demo;

import org.jspecify.annotations.NonNull;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.time.Clock;
import java.time.LocalTime;
import java.util.function.Supplier;

public record CustomAuthorizationManager(Clock clock) implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationResult authorize(
            @NonNull Supplier<? extends Authentication> authentication,
            RequestAuthorizationContext object
    ) {
        boolean isAfterMidday = LocalTime
                .now(clock)
                .isAfter(LocalTime.of(12, 0));
        return new AuthorizationDecision(isAfterMidday);
    }

}
