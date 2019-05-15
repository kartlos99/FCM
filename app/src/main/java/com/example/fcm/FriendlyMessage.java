package com.example.fcm;

public class FriendlyMessage {
    private String id, author, body;

    public FriendlyMessage() {
    }

    public FriendlyMessage(String id, String author, String body) {
        this.id = id;
        this.author = author;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
