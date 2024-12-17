## MessageController

### Description
This controller is 

### Annotations
- `@Restcontroller`: This annotation is used to define the class as a RestController.
- `@RequestMapping("/message")`: This annotation is used to define the base URL(`"/message"`) for the controller.

### Injected Services and Utilities
- `MessageService`: This service is used to handle the business logic for messages.
- `UserService`: This service is used to handle the business logic for users.
- `JwtUtil`: This utility is used to handle JWT token operations.

### Methods

#### `HandleChatroomMessage`:
- `@MessageMapping("/chatroom/{theme}")`: This annotation is used to define the endpoint for the message handling.
- `@SendTo("/topic/chatroom/{theme}")`: This annotation is used to define the destination for the message.
- **Parameters**:
    - `@Header("Authorization") String token`: Authorization header containing the JWT token.
    - `MessageDTO messageDTO`: Contains the content and theme of the message.
- **Validations**:
    - `token`: Must not be null or empty.
    - `messageDTO.theme`: Must not be null or empty.
    - `messageDTO.content`: Must not be null or empty.
- **message service**
    - creates a new message and saves it to the database using the `MessageService`.
- **returns**: The saved message converted to a `MessageDTO`.

#### `getMessages`:
- `@GetMapping("/chatroom/{theme}")`: This annotation is used to define the endpoint for getting messages.
- **Parameters**:
  - `@Header("Authorization) String token`: Authorization header containing the JWT token.
  - `@PathVariable String theme`: The theme of the chatroom.
- **Validations**:
  - `token`: Must not be null or empty.
  - `messages`: Must not be null or empty.
- **message service**
  - retrieves all messages for the given theme using the `MessageService`.
- **returns**: A list of messages converted to a list of `MessageDTO`.