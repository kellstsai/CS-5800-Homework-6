import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

//import javax.swing.text.html.HTMLDocument.Iterator;

import org.junit.Assert.*;

import com.example.ChatHistory;
import com.example.ChatServer;
import com.example.Message;
import com.example.MessageMemento;
import com.example.SearchMessageByUser;
import com.example.User;

import org.junit.*;

public class Junit {
    private ChatServer gameServer;
    private ChatServer server; 
    private User firstUser;
    private User secondUser; 
    private User thirdUser; 
    private List<Message> messages; 

    @Before
    public void setUp() {
        gameServer = new ChatServer();
        server = new ChatServer();
        firstUser = new User("Janet", gameServer);
        secondUser = new User("Derek", gameServer);
        thirdUser = new User("Kate", gameServer);
        gameServer.registerUser(firstUser);
        gameServer.registerUser(secondUser);
        gameServer.registerUser(thirdUser);
    }

    @Test
    public void sendMessageTest() {
        firstUser.sendMessage(Arrays.asList("Derek"), "How are you, Derek?");
        ChatHistory janetHistory = firstUser.getChatHistory(); 
        assertEquals(1, janetHistory.getSize()); 
    }

    @Test
    public void blockUserTest() {
        firstUser.blockUser("Kate");
        thirdUser.sendMessage(Arrays.asList("Janet"), "Hi Janet!");
        ChatHistory kateHistory = firstUser.getChatHistory(); 
        assertEquals(0, kateHistory.getSize());
    }

    @Test
    public void undoLastMessageSentTest(){
        firstUser.sendMessage(Arrays.asList("Kate"), "Let's meet up later.");
        firstUser.undoLastMessageSent();
        ChatHistory janetHistory = firstUser.getChatHistory();
        assertEquals(0, janetHistory.getSize()); 
    }

    @Test
    public void addMessageTest() {
        ChatHistory chatHistory = new ChatHistory();
        Message message = new Message("Janet", Arrays.asList("Derek"), null, "Hello, Derek!");
        chatHistory.addMessage(message);
        Message lastMessage = chatHistory.getLastMessage("Janet");
        assertNotNull(lastMessage);
        assertEquals(message, lastMessage);
    }

    @Test
    public void removeLastMessageTest(){
        ChatHistory chatHistory = new ChatHistory(); 

        Message firstMessage = new Message("Janet", Arrays.asList("Derek"), new Date(0), "First Message"); 
        Message secondMessage = new Message("Janet", Arrays.asList("Derek"), new Date(0), "Second Message"); 
        Message thirdMessage = new Message("Janet", Arrays.asList("Derek"), new Date(0), "Third Message"); 

        chatHistory.addMessage(firstMessage);
        chatHistory.addMessage(secondMessage);
        chatHistory.addMessage(thirdMessage);


        chatHistory.removeLastMessage("Janet");
        assertNotNull(chatHistory.getLastMessage("Janet")); 
    }

    @Test
    public void getLastMessageTest() {
        ChatHistory chatHistory = new ChatHistory();

        Message firstMessage = new Message("Janet", Arrays.asList("Derek"), new Date(0), "First Message"); 
        Message secondMessage = new Message("Janet", Arrays.asList("Derek"), new Date(0), "Second Message"); 
        Message thirdMessage = new Message("Janet", Arrays.asList("Derek"), new Date(0), "Third Message"); 

        chatHistory.addMessage(firstMessage);
        chatHistory.addMessage(secondMessage);
        chatHistory.addMessage(thirdMessage);

        Message lastMessage = chatHistory.getLastMessage("Janet"); 
        assertEquals(thirdMessage, lastMessage); 

    }

    @Test
    public void iteratorTest() {
        ChatHistory chatHistory = new ChatHistory();

        // Add some messages to the chat history
        Message message1 = new Message("Janet", Arrays.asList("Derek"), new Date(0), "Message 1");
        Message message2 = new Message("Derek", Arrays.asList("Janet"), new Date(0), "Message 2");
        Message message3 = new Message("Janet", Arrays.asList("Derek"), new Date(0), "Message 3");

        chatHistory.addMessage(message1);
        chatHistory.addMessage(message2);
        chatHistory.addMessage(message3);

        Iterator<Message> aliceIterator = chatHistory.iterator(new User("Janet", null));

        assertTrue(aliceIterator.hasNext());
        assertEquals(message1, aliceIterator.next());
        assertTrue(aliceIterator.hasNext());
    }

    @Before
    public void setUp2() {
        server = new ChatServer(); 
        firstUser = new User("Alice", new ChatServer());
        messages = new ArrayList<>();
        // Add some messages to the list
        messages.add(new Message("Bob", Arrays.asList("Alice"), new Date(0), "Hello Alice!"));
        messages.add(new Message("Charlie", Arrays.asList("Alice"), new Date(0), "Hi Alice!"));
    }

    @Test
    public void registerUserTest() {
        User user = new User("Janet", server); 
        server.registerUser(user);
        User registerUser = server.getUser("Janet");
        assertEquals("Registered user should match ", user, registerUser); 
    }

    @Test
    public void unregisterUserTest() {
        User user = new User("Janet", server); 
        server.registerUser(user);
        User registeUser = server.getUser("Janet"); 
        assertEquals("Registered user should match ", user, registeUser); 
        server.unregisterUser(user);
        registeUser = server.getUser("Janet");
        assertNull(registeUser);
    }

    @Test
    public void isBlockedTest() {
        String sender = "Derek"; 
        String recipient = "Janet";

        assertFalse(server.isBlocked(recipient, sender));
        server.blockUser(recipient, sender);
        assertTrue(server.isBlocked(recipient, sender));
    }

    @Test
    public void SearchMessageByUserTest() {
        firstUser = new User("Alice", new ChatServer());
        messages = new ArrayList<>();
        messages.add(new Message("Bob", Arrays.asList("Alice"), new Date(0), "Hello Alice!"));
        messages.add(new Message("Charlie", Arrays.asList("Alice"), new Date(0), "Hi Alice!"));
    }

    @Test
    public void hasNextTestTrue() {
        SearchMessageByUser search = new SearchMessageByUser(firstUser, messages);
        assertTrue(search.hasNext());
    }
    @Test
    public void testHasNextFalse() {
        SearchMessageByUser search = new SearchMessageByUser(firstUser, new ArrayList<>());
        assertFalse(search.hasNext());
    }

    @Test
    public void testNext() {
        SearchMessageByUser search = new SearchMessageByUser(firstUser, messages);
        assertNotNull(search.next());
    }

    @Test
    public void saveAndGetLastMessageMementoTest() {
        Message message = new Message("Derek", Arrays.asList("Janet"), new Date(0), "Hello Janet!");
        firstUser.saveLastMessageMemento(message);

        MessageMemento savedMemento = firstUser.getLastMessageMemento();

        assertNotNull(savedMemento); 

        assertEquals(message.getMessageContent(), savedMemento.getContent());
        assertEquals(message.getTimestamp(), savedMemento.getTimestamp());
    }

}

