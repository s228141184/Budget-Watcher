package com.example.budget_watcher;

public class Message {
    private String body;
    private String address;
    private String date;

    public Message(String body, String address, String date) {
        this.body = body;
        this.address = address;
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }
}
