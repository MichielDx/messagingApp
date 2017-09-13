package jstack.eu.messagingApp.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

public class Message implements Serializable {
    @Id
    private ObjectId id;
    private String message;
    @DBRef
    private User user;

    public Message() {
        id = new ObjectId();
    }

    public Message(String message, User user) {
        this();
        this.message = message;
        this.user = user;
    }



    public ObjectId getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
