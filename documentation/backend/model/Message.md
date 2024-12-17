# Message Model

## Description
The message model is a class that represents the `messages` table in the database.

## Annotations
- **`@Entity`**: Specifies that the class is an entity.
- **`@Getter, @Setter`**: Automatically generates getter and setter methods for the fields.
- **`@NoArgsConstructor`**: Generates a no-argument constructor.
- **`@Table(name="messages")`**: Specifies the name of the table in the database.

## Fields

- **`messageId`**: The ID of the message.
    - **`@Id`**: Specifies that this field is the primary key.
    - **`@GeneratedValue(strategy=GenerationType.IDENTITY)`**: Specifies that the value of this field is generated automatically.
    - **`@Column(name="message_id")`**: Specifies the name of the column in the database.

- **`user`**: The user who posted the message.
    - **`@ManyToOne`**: Specifies a many-to-one relationship with the `User` entity.
    - **`@JoinColumn(name="user_id", nullable=false)`**: Specifies the name of the foreign key column and ensures it cannot be null.

- **`content`**: The content of the message.
    - **`@Column(nullable=false, columnDefinition="TEXT")`**: Specifies that this field is a non-nullable column of type `TEXT`.

- **`createdAt`**: The date and time when the message was created.
    - **`@Column(name="created_at", nullable=false, updatable=false)`**: Specifies the name of the column in the database, ensures it cannot be null, and prevents updates to this field.

- **`theme`**: The chatroom theme associated with the message.
    - **`@Column(nullable=false)`**: Specifies that this field is a non-nullable column.