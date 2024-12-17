package hva.nl.backendelection.service;

import hva.nl.backendelection.model.Message;
import hva.nl.backendelection.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is responsible to save and get messages from the database.
 * It uses the MessageRepository to save and get messages.
 * the @Service annotation is used to indicate that this class is a service class.
 */
@Service
public class MessageService {

    // Injecting the MessageRepository
    @Autowired
    private MessageRepository messageRepository;

    /**
     * This method is used to save a message in the database.
     * @param message the message that needs to be saved.
     */
    public void saveMessage(Message message){
        messageRepository.save(message);
    }

    /**
     * This method is used to get all the messages connected to a specific theme.
     * @param theme the theme of the messages that needs to be retrieved from the repository.
     * @return an Iterable of messages with a specific theme.
     */
    public Iterable<Message> getMessagesByTheme(String theme){
        return messageRepository.findByTheme(theme);
    }
}
