## WebsocketConfig

### Annotations
- `@Configuration`: This annotation marks that WebSocketConfig is a configuration class.
- `@EnableWebSocketMessageBroker`: Enables websockets message handling, backed by a message broker.

### Implemented Interface
- `WebSocketMessageBrokerConfigurer`: Interface to configure message broker options.

### Methods
- `configureMessageBroker(MessageBrokerRegistry config)`: 
configures the message broker for message handling.
- `Parameter`: MessageBrokerRegistry registry
- **Key configurations**:
- `registry.enableSimpleBroker("/topic")`: enables a simple in-memory message broker. Users subscribe to `/topic` to receive messages.
- `registry.setApplicationDestinationPrefixes("/app")`: defines the prefix for messages sent from clients to the server.

- `registerStompEndpoints(StompEndpointRegistry registry)`:
registers the endpoint for the websocket connection.
- `Parameter`: StompEndpointRegistry registry
- **Key configurations**:
- `registry.addEndpoint("/chatroom")`: registers the endpoint for the websocket connection. users connect to `/chatroom`.
- `.setAllowedOrigins("*")`: allows all origins to connect to the websocket.
- `.withSockJS()`: enables SockJS fallback options for browsers that do not support websocket.

