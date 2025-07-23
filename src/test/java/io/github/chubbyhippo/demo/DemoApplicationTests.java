package io.github.chubbyhippo.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class WhenExpressionIsTrueTest {

    @Autowired
    private MockMvcTester mockMvcTester;
    @MockitoBean
    private Clock clock;

    private void setClock(String time) {
        var instant = Instant.parse("2025-07-23T" + time + "Z");
        var zone = ZoneId.of("UTC");
        var fixedClock = Clock.fixed(instant, zone);
        Mockito.when(clock.instant()).thenReturn(fixedClock.instant());
        Mockito.when(clock.getZone()).thenReturn(fixedClock.getZone());
    }

    @Test
    @DisplayName("should return hello")
    @WithMockUser
    void shouldReturnHello() {

        setClock("14:00:00.00");
        mockMvcTester.get()
                .uri("/hello")
                .assertThat()
                .hasStatusOk()
                .bodyText()
                .isEqualTo("Hello!");
    }

    @Test
    @DisplayName("should return 4xx before midday")
    void shouldReturn4XxBeforeMidday() {
        setClock("11:00:00.00");
        mockMvcTester.get()
                .uri("/hello")
                .assertThat()
                .hasStatus4xxClientError();
    }
}
