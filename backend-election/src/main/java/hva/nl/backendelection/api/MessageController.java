package hva.nl.backendelection.api;

import hva.nl.backendelection.dto.MessageDTO;
import hva.nl.backendelection.model.Message;
import hva.nl.backendelection.service.MessageService;
import hva.nl.backendelection.service.UserService;
import hva.nl.backendelection.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class used for handling message related tasks.
 * This class is used to handle WebSocket messages.
 *
 * @RestController indicates that this class is a controller.
 * @RequestMapping("/message") maps HTTP requests to a specific path("/message").
 */

@RestController
@RequestMapping("/message")
public class MessageController {

    // Autowire the required services and utilities

    // Use the messageService to save and retrieve messages.
    @Autowired
    private MessageService messageService;

    // Use the userService to retrieve user information.
    @Autowired
    private UserService userService;

    // Use the jwtUtil to extract user information from the token.
    @Autowired
    private JwtUtil jwtUtil;

    // Use the messagingTemplate to convert and send messages to the WebSocket.
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Handles the chatroom messages.
     * This method is used to handle messages sent to the theme specific chatroom.
     * The message is saved in the database and sent to the chatroom topic(WebSocket).
     * The message is also converted to a DTO before sending and returning.
     *
     * @MessageMapping("/chatroom/{theme}") maps the message to the chatroom topic.
     * @SendTo("/topic/chatroom/{theme}") sends the message to the chatroom topic.
     *
     * @param token used to validate the user.
     * @param messageDTO used to get the content and theme.
     * @return messageDTO.
     */
    @MessageMapping("/chatroom/{theme}")
    @SendTo("/topic/chatroom/{theme}")
    public MessageDTO handleChatroomMessage(@Header("Authorization") String token, MessageDTO messageDTO) {
//        validate token
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token is required.");
        }

        if (messageDTO.getTheme() == null || messageDTO.getTheme().isEmpty()) {
            throw new IllegalArgumentException("Theme is required.");
        }

        if (messageDTO.getContent() == null || messageDTO.getContent().isEmpty()) {
            throw new IllegalArgumentException("Content is required.");
        }

        Long userId = jwtUtil.extractUserId(token);

        // Create and save the message
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setTheme(messageDTO.getTheme());
        message.setUser(userService.getUserById(userId)); // Ensure `getUserById` handles user not found errors
        messageService.saveMessage(message);

        // Convert and return the saved message as a DTO
        return convertToDTO(message);
    }


    /**
     * Get messages by theme.
     * This method is used to get messages by theme.
     *
     * @GetMapping ("/get/{theme}") maps the request to get messages by theme.
     *
     * @param token used to validate the user through the JWTFilter.
     * @param theme used to get the messages by theme.
     * @return a list of messages by theme.
     */
    @GetMapping("/get/{theme}")
    public ResponseEntity<Iterable<MessageDTO>> getMessages(@RequestHeader("Authorization") String token, @PathVariable String theme) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token is required.");
        }

        Iterable<Message> messages = messageService.getMessagesByTheme(theme);
        if (messages == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertToDTO(messages));
    }

    /**
     * User joined the chat.
     * This method is used to broadcast a message when a user joins the chat. So, other users in the chatroom can see that a user has joined.
     * The message gets converted to a messaging template and sent to the chatroom topic.
     *
     * @MessageMapping ("/join/{theme}") maps the message to the chatroom topic.
     *
     * @param token used to validate the user automatically through the JWTFilter and manually in this method.
     * @param theme used to get the chatroom theme.
     */
    @MessageMapping("/join/{theme}")
    public void userJoined(@Header("Authorization") String token, @DestinationVariable String theme) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token is required.");
        }

        String username = jwtUtil.extractUsername(token);
        String userJoinedMessage = username + " has joined the chat!";

        // Broadcast the message to the chatroom topic
        messagingTemplate.convertAndSend("/topic/chatroom/" + theme, userJoinedMessage);
    }



    private MessageDTO convertToDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessageId(message.getMessageId());
        messageDTO.setContent(message.getContent());
        messageDTO.setCreatedAt(message.getCreatedAt());
        messageDTO.setUserId(message.getUser().getUserId());
        messageDTO.setUsername(message.getUser().getUsername());
        return messageDTO;
    }

    private Iterable<MessageDTO> convertToDTO(Iterable<Message> messages) {
        List<MessageDTO> messageDTOs = new ArrayList<>();
        for (Message message : messages) {
            messageDTOs.add(convertToDTO(message));
        }
        return messageDTOs;
    }


}
