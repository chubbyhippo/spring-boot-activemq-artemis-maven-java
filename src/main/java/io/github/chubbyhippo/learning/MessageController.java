package io.github.chubbyhippo.learning;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    private final JmsTemplate jmsTemplate;
    private final MessageListenerService messageListenerService;

    public MessageController(JmsTemplate jmsTemplate, MessageListenerService messageListenerService) {
        this.jmsTemplate = jmsTemplate;
        this.messageListenerService = messageListenerService;
    }

    @PostMapping("/send")
    void send(@RequestBody String message) {
        jmsTemplate.convertAndSend("test-queue", message);
    }

    @PostMapping("/receive")
    List<String> receive() {
        return messageListenerService.getReceivedMessages();
    }
}
