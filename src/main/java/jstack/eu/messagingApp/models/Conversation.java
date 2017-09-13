package jstack.eu.messagingApp.models;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Conversation {
    @Id
    private String id;

    private String name;

    private List<Message> messages;

    public Conversation() {
        messages = new ArrayList<>();
    }

    public Conversation(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
