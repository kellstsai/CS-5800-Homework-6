package com.example;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class SearchMessageByUser implements Iterator<Message>{
    private User userToSearchWith; 
    private Iterator<Message> iterator; 
    private Message nextMessage; 

    public SearchMessageByUser(User userToSearchWith, List<Message> messages) {
        this.userToSearchWith = userToSearchWith; 
        this.iterator = messages.iterator();
        findNextMessage(); 
    }

    @Override
    public boolean hasNext() {
        return nextMessage != null;
    }

    @Override
    public Message next() {
        if (nextMessage == null) {
            throw new NoSuchElementException();
        }
        Message message = nextMessage;
        findNextMessage();
        return message;
    }

    private void findNextMessage() {
        nextMessage = null;
        while (iterator.hasNext()) {
            Message message = iterator.next();
            if (message.getSender().equals(userToSearchWith.getUserName())
                    || message.getRecipient().contains(userToSearchWith.getUserName())) {
                nextMessage = message;
                break;
            }
        }
    }
    
}
