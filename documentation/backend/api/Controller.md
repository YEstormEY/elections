# UserController 

## Description
the `UserController` class is a REST controller that handles HTTP requests related to user management, such as registering and logging in. It interacts with the `UserService` to perform user-related operations.

## Annotations
- `@RestController`: Marks the class as a REST controller, allowing it to handle HTTP requests.
- `@RequestMapping("/auth")`: Specifies the base URL path for the controller.

## Dependencies
- `UserService`: Provides methods related to user management, in this case login and register.

## Endpoints

### `POST /register`
- URL: `/auth/register`
- Method: `POST`
- Request Body:
  - `username`: The username of the new user.
  - `password`: The password of the new user.
  - `confirmPassword`: The confirmation password of the new user(it must match).
  - `email`: The email address of the new user.
- @Return
  - `User`: The newly registered user.
- logic:
  - Check if the username or email already exists in the database.
  - Encrypts the password using the `PasswordEncoder`.
  - Assigns the role of the user by default as user.
  - Saves the user in the database using the `UserService`.

### `POST /login`
- URL: `/auth/login`
- Method: `POST`
- Request Body:
  - `username`: The username of the user.
  - `password`: The password of the user.
- @Return
  - `User`: The logged-in user if the credentials are valid.
- logic:
  - Retrieves the user from the database using the `UserService`.
  - Compares the password with the stored encrypted password using the `PasswordEncoder`.