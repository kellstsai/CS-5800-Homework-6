package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer {
    private Map<String, User> users;
    private Map<String, List<String>> blockedUsers; 

    public ChatServer() {
        users = new HashMap<>();
        blockedUsers = new HashMap<>(); 
    }

    public void registerUser(User user) {
        users.put(user.getUserName(), user); 
    }

    public void unregisterUser(User user) {
        users.remove(user.getUserName());
    }

    
    public void blockUser(String blocker, String userBlock) {
        if(!blockedUsers.containsKey(blocker)) {
            blockedUsers.put(blocker, new ArrayList<>()); 
        }
        blockedUsers.get(blocker).add(userBlock);
    }

    public boolean isBlocked(String recipient, String sender) {
        List<String> blocked = blockedUsers.get(recipient); 
        return blocked != null && blocked.contains(sender);
    }
    

    public void sendMessage(Message message) {
        List<String> recipients = message.getRecipient(); 
        for(int i = 0; i < recipients.size(); i++) {
            String recipient = recipients.get(i);
            if(!isBlocked(recipient, message.getSender())) {
                User user = users.get(recipient); 
                if(user != null) {
                    user.receiveMessage(message);
                }
            }
        }
    }

    public User getUser(String username) {
        return users.get(username); 
    }
}
