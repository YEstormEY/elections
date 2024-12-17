package hva.nl.backendelection.api;

import hva.nl.backendelection.model.request.LoginRequest;
import hva.nl.backendelection.model.request.RegisterRequest;
import hva.nl.backendelection.model.User;
import hva.nl.backendelection.model.request.UpdatePasswordRequest;
import hva.nl.backendelection.model.request.UpdateUserRequest;
import hva.nl.backendelection.service.UserService;
import hva.nl.backendelection.utility.JwtUtil;
import hva.nl.backendelection.utility.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * The UserController class is used to handle the API endpoints related to the User model.
 */
@RestController
@RequestMapping("/auth")
public class UserController {

    /**
     * The UserService class is used to handle the business logic of the UserController class.
     */
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;



    /**
     * Registers a new user based on the provided registration details.
     * The method checks if the password and confirm password match. If they don't, it throws a RuntimeException.
     *
     * @param registerRequest Object containing the username, password, confirm password, and email for registration.
     * @return ResponseEntity containing the registered user.
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterRequest registerRequest) {
        // Check if password and confirm password match
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        // Check if username is provided
        if (registerRequest.getUsername() == null || registerRequest.getUsername().trim().isEmpty()) {
            throw new RuntimeException("Username is required");
        }

        // Check if email is provided and valid format (simple regex for demonstration)
        if (registerRequest.getEmail() == null || registerRequest.getEmail().trim().isEmpty() ||
                !registerRequest.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new RuntimeException("A valid email is required");
        }

        // Check if password meets minimum length requirement
        if (registerRequest.getPassword().length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters long");
        }

        // Check if username or email already exists
        if (userService.getUser(registerRequest.getUsername()) != null ||
                userService.getUserByEmail(registerRequest.getEmail()) != null) {
            throw new RuntimeException("User with the given username or email already exists");
        }

        // Register user
        User user = userService.registerUser(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail());
        return ResponseEntity.ok(user);
    }


    /**
     * Logs in a user based on the provided login details.
     *
     * @param loginRequest Object containing the username and password for login.
     * @return ResponseEntity containing the logged-in user.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        // Authenticate the user with provided username and password
        User user = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());

        // Generate a JWT token for the authenticated user
        String token = jwtUtil.generateToken(user.getUsername(), user.getUserId(), user.getRole());

        // Extract token expiration date
        Date expirationDate = jwtUtil.extractExpiration(token);
        long expirationTimeMillis = expirationDate.getTime();

        // Return the token, expiration time, and user ID to the client
        return ResponseEntity.ok(new AuthenticationResponse(token, expirationTimeMillis, user.getUserId()));
    }

    /**
     * Retrieves a user based on the provided token.
     *
     * @param token JWT token containing the user ID.
     * @return ResponseEntity containing the user.
     */
    @GetMapping("/protected/user")
    public ResponseEntity<User> getUser(@RequestHeader("Authorization") String token) {
        Long userId = jwtUtil.extractUserId(token);
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    /**
     * Deletes a user based on the provided token.
     *
     * @param token JWT token containing the user ID.
     * @return ResponseEntity containing a message indicating the user was deleted.
     */
    @DeleteMapping("/protected/delete/user")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token) {
        userService.deleteUser(jwtUtil.extractUserId(token));
        return ResponseEntity.ok("User deleted");
    }

    /**
     * Updates a user.
     * Checks if the email or username already exists.
     * If the email or username already exists, it returns a bad request.
     * If the email or username isn't already in use, it updates the user.
     *
     * @PostMapping ("/protected/update/user") Sets the endpoint for updating a user.
     *
     * @param updateUserRequest Request body containing the user ID, username, and email.
     * @return A new JWT token for the user.
     */
    @PostMapping("/protected/update/user")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {

        User emailCheck = userService.getUserByEmail(updateUserRequest.getEmail());
        User usernameCheck = userService.getUserByUsername(updateUserRequest.getUsername());


        if (emailCheck != null || usernameCheck != null) {
            User user = userService.getUserById(updateUserRequest.getUserId());
            if (emailCheck != null && emailCheck.getUserId() != user.getUserId()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
            }
            if (usernameCheck != null && usernameCheck.getUserId() != user.getUserId()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
            }
        }

        User user = userService.updateUser(updateUserRequest.getUserId(), updateUserRequest.getUsername(), updateUserRequest.getEmail());

        // Generate a JWT token for the user
        String token = jwtUtil.generateToken(user.getUsername(), user.getUserId(), user.getRole());
        // Get the token's expiration time
        Date expirationDate = jwtUtil.extractExpiration(token);
        long expirationTimeMillis = expirationDate.getTime();
        Long userId = user.getUserId();


        return ResponseEntity.ok(new AuthenticationResponse(token, expirationTimeMillis, userId));
    }

    /**
     * Updates a user's password.
     * Checks if the current password is correct.
     * Checks if the new password is at least 8 characters long.
     * If the current password is incorrect or the new password is too short, it returns a bad request with a message.
     *
     * @PostMapping ("/protected/update/password") Sets the endpoint for updating a user's password.
     *
     * @param token used to check the user and extract the userId.
     * @param updatePasswordRequest Request body containing the user ID, and new password.
     * @return ResponseEntity containing the updated user.
     */
    @PostMapping("/protected/update/password")
    public ResponseEntity<?> updatePassword(@RequestHeader("Authorization") String token, @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        User currentPass = userService.getUserById(jwtUtil.extractUserId(token));

        String newPass = updatePasswordRequest.getPassword();

        if (!passwordEncoder.matches(newPass, currentPass.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password is incorrect");
        }

        if (updatePasswordRequest.getNewPassword().length() < 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be at least 8 characters long");
        }

        System.out.println(updatePasswordRequest.getPassword());

        User user = userService.updatePassword(updatePasswordRequest.getUserId(), updatePasswordRequest.getPassword(), updatePasswordRequest.getNewPassword());

        return ResponseEntity.ok(user);
    }
}
