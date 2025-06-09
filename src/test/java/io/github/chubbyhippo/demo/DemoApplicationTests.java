package io.github.chubbyhippo.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.time.LocalTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    private MockMvcTester mockMvcTester;

    @Test
    @DisplayName("should return hello")
    @WithMockUser
    void shouldReturnHello() {
        if (LocalTime.now().isAfter(LocalTime.of(12, 0))) {
            mockMvcTester.get()
                    .uri("/hello")
                    .assertThat()
                    .hasStatusOk()
                    .bodyText()
                    .isEqualTo("Hello!");
        }
    }

    @Test
    @DisplayName("should return 403")
    @WithMockUser
    void shouldReturn403() {
        if (!LocalTime.now().isAfter(LocalTime.of(12, 0))) {
            mockMvcTester.get()
                    .uri("/hello")
                    .assertThat()
                    .hasStatus4xxClientError();
        }
    }
}
