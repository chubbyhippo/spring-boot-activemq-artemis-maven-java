package io.github.chubbyhippo.learning;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    private final MessageSenderService messageSenderService;
    private final MessageListenerService messageListenerService;

    public MessageController(MessageSenderService messageSenderService, MessageListenerService messageListenerService) {
        this.messageSenderService = messageSenderService;
        this.messageListenerService = messageListenerService;
    }

    @PostMapping("/send")
    void send(@RequestBody String message) {
        messageSenderService.send(message);
    }

    @PostMapping("/receive")
    List<String> receive() {
        return messageListenerService.getReceivedMessages();
    }
}
