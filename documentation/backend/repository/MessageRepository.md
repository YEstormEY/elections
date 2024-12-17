## Message Repository

### Description
The message repository is an interface that extends the `JpaRepository` interface. It provides methods to interact with the `messages` table in the database.

### Methods
- **`Iterable<Message> findByTheme(String theme);`**
    - Finds all messages by their associated theme.
    - **`theme`**: The theme or chatroom category to filter messages by.
    - Returns an iterable of messages.