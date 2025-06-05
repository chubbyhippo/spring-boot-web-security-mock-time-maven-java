package io.github.chubbyhippo.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.time.LocalTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class DemoApplicationTests {

    @Autowired
    private MockMvcTester mockMvcTester;

    @Test
    @DisplayName("should return hello")
    @WithMockUser
    void shouldReturnHelloOr403() {
        if (LocalTime.now().isAfter(LocalTime.of(12, 0))){
            mockMvcTester.get()
                    .uri("/hello")
                    .assertThat()
                    .hasStatusOk()
                    .bodyText()
                    .isEqualTo("Hello!");
        } else {
            mockMvcTester.get()
                    .uri("/hello")
                    .assertThat()
                    .hasStatus4xxClientError();
        }

    }

}
