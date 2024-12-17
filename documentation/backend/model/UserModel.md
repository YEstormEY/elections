# User Model

## Description
the user model is a class that represents the user table in the database.

## Annotations
- `@Entity`: specifies that the class is an entity.
- `@Getter, @Setter`: automatically generates getter and setter methods for the fields.
- `@NoArgsConstructor`: generates a no-argument constructor.
- `@Table`: specifies the name of the table in the database.

## Fields
- `id`: the id of the user.
- - the `@Id` annotation specifies that this field is the primary key.
- - the `@Column` annotation specifies the name of the column in the database.
- - the `@GeneratedValue` annotation specifies that the value of this field is generated automatically.


- `username`: the username of the user.
- - the `@Column` annotation specifies the name of the column in the database.


- `email`: the email of the user.
- - the `@Column` annotation specifies the name of the column in the database.


- `password`: the password of the user.
- - the `@Column` annotation specifies the name of the column in the database.


- `role` the role of the user.
- - the `@Column` annotation specifies the name of the column in the database.
