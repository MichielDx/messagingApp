package jstack.eu.messagingApp.models;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

public class Message implements Serializable {
    private String message;
    @DBRef
    private User user;
    private String username;

    public Message(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public Message(String message, String username) {
        this.message = message;
        this.username = username;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
