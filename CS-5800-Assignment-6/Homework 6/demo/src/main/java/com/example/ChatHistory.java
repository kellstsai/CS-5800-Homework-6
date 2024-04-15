package com.example;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ChatHistory implements IterableUser {
    private List<Message> history; 

    public ChatHistory() {
        history = new ArrayList<>(); 
    }


    public void addMessage(Message message) {
        history.add(message);
    }

    public void removeLastMessage(String name) {
        int index = -1; 
        for(int i = 0; i < history.size(); i++) {
            if(name.equals(history.get(i).getSender())) {
                index = i; 
            }
        }
        if(index != -1) {
            history.remove(index); 
            System.out.println("Message was successfully removed.");
        }
        else {
            System.out.println("There is no message to undo.");
        }
    }

    public Message getLastMessage(String name) {
        //return history.isEmpty() ? null : history.get(history.size() - 1);
        
        int index = -1; 
        for(int i = 0; i < history.size(); i++) {
            if(name.equals(history.get(i).getSender())) {
                index = i; 
            }
        }
        if(index != -1) {
            return history.get(index); 
        }
        else {
            return null;
        }
    }

    public void printChat() {
        for(Message message : history) {
            System.out.println(message.getSender() + " to " + message.getRecipient() + ": " + message.getMessageContent()); 
        }
    }

    public int getSize() {
        return history.size(); 
    }


    @Override
    public Iterator<Message> iterator(User userToSearchWith) {
        return new SearchMessageByUser(userToSearchWith, history); 
    }
}
