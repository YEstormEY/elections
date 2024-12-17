package hva.nl.backendelection.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * Configuration class for WebSocket.
 * With Spring WebSocket, we can send messages to clients in real-time.
 * STOMP is used to communicate between the server and the client.
 *
 * @Configuration indicates that this class is a configuration class.
 * @EnableWebSocketMessageBroker enables WebSocket message handling, backed by a message broker.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configure the message broker.
     * The message broker is responsible for routing messages between clients.
     * We enable a simple broker that routes messages from the server to clients.
     * We also set the application destination prefix to /app.
     * @param registry the message broker registry.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // Prefix used for subscribing to messages
        registry.setApplicationDestinationPrefixes("/app"); // Prefix used for sending messages
    }

    /**
     * Registers the STOMP endpoints.
     * STOMP is a simple text-orientated messaging protocol.
     * We register the /chatroom endpoint.
     * We also allow all origins to connect to the WebSocket.
     * We enable SockJS fallback for browsers that do not support WebSocket.
     * @param registry the STOMP endpoint registry.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chatroom") // Main WebSocket endpoint
                .setAllowedOriginPatterns("*") // Allows all origins to connect to the WebSocket
                .withSockJS(); // Enable SockJS fallback for browsers that do not support WebSocket
    }
}
