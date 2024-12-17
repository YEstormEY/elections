# UserService

## Description
the `UserService` class provides methods related to user management, such as loggin in or registering users.
It interacts with the `UserRepository` to store or retrieve user data from the database, and it uses PasswordEncoder for password encryption.

## Annotations
- `@Service`: Marks the class as a service component in Spring, allowing it to be injected into other classes and being used within the application.

## Dependencies
- `UserRepository`: Provides methods to interact with the user table in the database.
- `PasswordEncoder`: Used to encode and decode passwords for secure storage and retrieval.

## Methods
`registerUser(String username, String password, String email)`
Registers a new user with the given username, password, and email address.

The password is encrypted with the PasswordEncoder before being stored in the database.

- **@Param**
- - `username`: The username of the new user.
- - `password`: The password of the new user.
- - `email`: The email address of the new user.

- **@Return**
- - `User`: The newly registered user.

- **Logic:**
- - Check if the username or email already exists in the database.
- - Encrypts the password using the `PasswordEncoder`.
- - Assigns the role of the user by default as user.
- - Saves the user in the database using the `UserRepository`.


`loginUser(String username, String password)`
Logs in a user with the given username and password.

- **@Param**
- - `username`: The username of the user.
- - `password`: The password of the user.

- **@Return**
- - `User`: The logged-in user if the credentials are valid.

- **Logic:**
- - Retrieves the user from the database using the `UserRepository`.
- - Compares the password with the stored encrypted password using the `PasswordEncoder`.