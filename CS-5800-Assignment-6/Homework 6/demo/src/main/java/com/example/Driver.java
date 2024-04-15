package com.example;

import java.util.Arrays;
import java.util.Iterator;

public class Driver {
    public static void main(String[] args) {
        ChatServer server = new ChatServer();

        User user1 = new User("Janet", server);
        User user2 = new User("Derek", server);
        User user3 = new User("Latte", server);

        server.registerUser(user1);
        server.registerUser(user2);
        server.registerUser(user3);

        user1.sendMessage(Arrays.asList("Derek", "Latte"), "Hello Derek and Latte"); 
        user2.sendMessage(Arrays.asList("Janet"), "Hi Janet!");
        user3.sendMessage(Arrays.asList("Janet", "Derek"), "Hey Janet and Derek!");

         
        System.out.println("******************");
        System.out.println("CHAT HISTORY");
        user1.viewChatHistory();
        user2.viewChatHistory();
        user3.viewChatHistory();
        System.out.println("******************");
        

        System.out.println("----------------------");
        System.out.println("UNDO LAST MESSAGE"); 
        user1.undoLastMessageSent();

        System.out.println("Chat history after undoing last message."); 
        user1.viewChatHistory();
        System.out.println("----------------------");


        System.out.println("=====================");
        System.out.println("BLOCK USER"); 
        user2.blockUser("Janet");
        user1.sendMessage(Arrays.asList("Derek", "Latte"), "This message should be blocked.");
        System.out.println("Blocked for Derek");
        System.out.println("=====================");

        // Iterate over messages sent or received by user1
        System.out.println("+++++++++++++++++++++");
        System.out.println("Messages sent or received by user3:");
        Iterator<Message> user3Iterator = user3.iterator();
        while (user3Iterator.hasNext()) {
            Message message = user3Iterator.next();
            System.out.println(message.getSender() + " to " + message.getRecipient() + ": " + message.getMessageContent());
        }  
        System.out.println("+++++++++++++++++++++");
    }
}