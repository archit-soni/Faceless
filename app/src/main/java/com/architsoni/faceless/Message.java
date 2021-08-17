package com.architsoni.faceless;

public class Message {
    private String text;
    private String sender;
    private String receiver;
    private long time;

    public Message(String text, String sender, String receiver, long time) {
        this.text = text;
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
    }
    public Message(){
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
