## MessageService

### Annotations
- `@Service`: This annotation marks that MessageService is a service class.

### Injected Dependencies
- `MessageRepository`: repository for messages.

### Methods
- `saveMessage(Message message)`:
- saves a message to the database.
- `Parameter`: Message message
- `Return`: Message

- `getMessagesByTheme(String theme)`:
- retrieves all messages by theme.
- `Parameter`: String theme
- `Return`: Iterable<Message>

