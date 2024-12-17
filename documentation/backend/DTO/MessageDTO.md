# Message DTO

## Description
This DTO is used to return a message object from the backend to the frontend.

## Annotations
- **`@Data`**: Lombok annotation to generate getters, setters, `equals`, `hashCode`, and `toString` methods.
- **`@NoArgsConstructor`**: Lombok annotation to generate a no-argument constructor.
- **`@AllArgsConstructor`**: Lombok annotation to generate a constructor with all fields.

## Fields
- **`messageId`**: The unique identifier of the message.
- **`userId`**: The ID of the user who posted the message.
- **`username`**: The username of the user who posted the message.
- **`content`**: The content of the message.
- **`createdAt`**: The date and time when the message was created.
- **`theme`**: The theme of the chatroom associated with the message.