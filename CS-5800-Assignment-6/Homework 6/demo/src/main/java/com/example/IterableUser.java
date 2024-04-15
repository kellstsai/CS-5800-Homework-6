package com.example;

import java.util.Iterator;

public interface IterableUser {
    Iterator<Message> iterator(User userToSearchWith); 
}
