# Comment model

## Description
the comment model is a class that represents the comment table in the database.

## Annotations
- `@Entity`: specifies that the class is an entity.
- `@Getter, @Setter`: automatically generates getter and setter methods for the fields.
- `@NoArgsConstructor`: generates a no-argument constructor.
- `@Table`: specifies the name of the table in the database.

## Fields
- `commentId`: the id of the comment.
- - the `@Id` annotation specifies that this field is the primary key.
- - the `@GeneratedValue` annotation specifies that the value of this field is generated automatically.
- - the `@Column` annotation specifies the name of the column in the database. 

- `article`: the article that the comment is associated with.
- - the `@ManyToOne` annotation specifies that this field is a many-to-one relationship with the article table.
- - the `@JoinColumn(name = "article_id"` annotation specifies the name of the foreign key `article_id` in the comment table.

- `user`: the user who made the comment.
- - the `@ManyToOne` annotation specifies that this field is a many-to-one relationship with the user table.
- - the `@JoinColumn(name = "user_id")` annotation specifies the name of the foreign key `user_id` in the comment table.

- `content`: the content of the comment.
- - the `@Column(nullable = false)` annotation specifies that this field is a column in the comment table and cannot be null.

- `createdAt`: the date and time when the comment was created.
- - the `@Column(nullable = false, updatable = false)` annotation specifies that this field is a column in the comment table and cannot be null. the `updatable = false` attribute specifies that this field cannot be updated.

- `updatedAt`: the date and time when the comment was last updated.
- - the `@Column(nullable = false)` annotation specifies that this field is a column in the comment table and cannot be null.

    
## Methods
- `prePersist()`: the method is called before the entity is saved(persisted) to the database. It sets the `createdAt` field to the current date and time.
- - the `@PrePersist` annotation specifies that this method should be called before the entity is saved into the database.

- `preUpdate()`: the method is called before the entity is updated in the database. It sets the updatedAt field to the current date and time.
- - the `@PreUpdate` annotation specifies that this method should be called before the entity is saved into the database.