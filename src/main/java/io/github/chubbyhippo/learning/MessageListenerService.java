package io.github.chubbyhippo.learning;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class MessageListenerService {
    private final List<String> receivedMessages = new CopyOnWriteArrayList<>();

    public List<String> getReceivedMessages() {
        return receivedMessages;
    }

    @JmsListener(destination = "test-queue")
    public void onMessage(String message) {
        receivedMessages.add(message);
    }
}
