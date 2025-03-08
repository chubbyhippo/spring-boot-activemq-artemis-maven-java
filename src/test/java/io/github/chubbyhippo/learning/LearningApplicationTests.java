package io.github.chubbyhippo.learning;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@Import(TestcontainersConfiguration.class)
@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LearningApplicationTests {

    @Autowired
    private MockMvcTester mockMvcTester;
    @Autowired
    private MessageListenerService messageListenerService;

    @Test
    @DisplayName("should send and receive jms message")
    void shouldSendAndReceiveJmsMessage() {
        mockMvcTester.post()
                .uri("/send")
                .content("hello")
                .exchange();

        await().atMost(5, TimeUnit.SECONDS)
                .until(() -> {
                    System.out.println(messageListenerService.getReceivedMessages());
                    return messageListenerService.getReceivedMessages()
                            .contains("hello");
                });
    }

    @Test
    @DisplayName("should send then receive message via endpoints")
    void shouldSendThenReceiveMessageViaEndpoints() {
        mockMvcTester.post()
                .uri("/send")
                .content("hello2")
                .exchange();

        await().atMost(3, TimeUnit.SECONDS)
                .until(() -> mockMvcTester.get()
                        .uri("/receive")
                        .exchange()
                        .getResponse()
                        .getContentAsString()
                        .contains("hello2"));
    }
}
