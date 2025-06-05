package io.github.chubbyhippo.demo;

import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class TimeChecker {

    public Boolean isAfter12Pm() {
        return LocalTime.now().isAfter(LocalTime.of(12, 0));
    }
}
