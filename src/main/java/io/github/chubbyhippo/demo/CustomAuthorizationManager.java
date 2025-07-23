package io.github.chubbyhippo.demo;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.time.Clock;
import java.time.LocalTime;
import java.util.function.Supplier;

public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final Clock clock;

    public CustomAuthorizationManager(Clock clock) {
        this.clock = clock;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        boolean isAfterMidday = LocalTime.now().isAfter(LocalTime.of(12, 0));
        return new AuthorizationDecision(isAfterMidday);
    }
}
