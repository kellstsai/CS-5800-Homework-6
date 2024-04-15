package com.example;

import java.util.*;

public class Message {
    private String sender;
    private List<String> recipient;  
    private Date timestamp;
    private String messageContent; 

    public Message(String sender, List<String> recipient, Date timestamp, String messageContent) {
        this.sender = sender; 
        this.recipient = recipient;
        this.timestamp = timestamp;
        this.messageContent = messageContent; 
    }

    public String getSender() {
        return sender; 
    }

    public List<String> getRecipient() {
        return recipient; 
    }

    public Date getTimestamp() {
        return timestamp; 
    }

    public String getMessageContent() {
        return messageContent; 
    }
}
