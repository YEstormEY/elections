package hva.nl.backendelection.api;

import hva.nl.backendelection.model.User;
import hva.nl.backendelection.model.request.UpdateUserRequest;
import hva.nl.backendelection.service.AdminService;
import hva.nl.backendelection.service.UserService;
import hva.nl.backendelection.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * The AdminController class is used to handle the API endpoints related to the Admin model.
 */
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    /**
     * The JwtUtil class is used to handle the JWT token.
     * The UserService class is used to handle the business logic of the AdminController class.
     * The AdminService class is used to handle the business logic of the AdminController class.
     */

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;


    /**
     * Retrieves all users from the database.
     * The method checks if the user is an admin. If the user is not an admin, it returns a BAD_REQUEST status.
     *
     * @param token The JWT token.
     * @return ResponseEntity containing all users.
     */
    @GetMapping("/allusers")
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String token) {

        // Check if the user is an admin

        if (jwtUtil.extractRole(token).equals("ADMIN")) {
            Iterable<User> users = adminService.getAllUsers();
            return ResponseEntity.ok(users);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not an admin");
        }

    }

    /**
     * Deletes a user from the database based on the provided user details.
     * The method checks if the user is an admin. If the user is not an admin, it returns a BAD_REQUEST status.
     *
     * @param token The JWT token.
     * @param user Object for getting the user info.
     * @return ResponseEntity containing a message indicating that the user has been deleted.
     */
    @PostMapping("/delete/user")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token, @RequestBody User user) {

        if (jwtUtil.extractRole(token).equals("ADMIN")) {
            Long userId = (user.getUserId());
            adminService.deleteUser(userId);
            return ResponseEntity.ok("User deleted");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not an admin");
        }
    }

    /**
     * Updates a user in the database based on the provided user details.
     * The method checks if the user is an admin. If the user is not an admin, it returns a BAD_REQUEST status.
     *
     * @param token The JWT token.
     * @param updateUserRequest Object for getting the user info.
     * @return ResponseEntity containing a message indicating that the user has been updated.
     */
    @PostMapping("/update/user")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String token, @RequestBody UpdateUserRequest updateUserRequest) {
        if (jwtUtil.extractRole(token).equals("ADMIN")) {

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

             User user = adminService.updateUser(updateUserRequest.getUserId(), updateUserRequest.getUsername(), updateUserRequest.getEmail(), updateUserRequest.getRole());
             adminService.updateUser(user.getUserId(), user.getUsername(), user.getEmail(), user.getRole());
             return ResponseEntity.ok("User updated");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not an admin");
    }

    /**
     * Creates a user in the database based on the provided user details.
     * The method checks if the user is an admin. If the user is not an admin, it returns a BAD_REQUEST status.
     *
     * @param token The JWT token.
     * @param user Object for getting the user info.
     * @return ResponseEntity containing a message indicating that the user has been created.
     */
    @PostMapping("/create/user")
    public ResponseEntity<?> createUser(@RequestHeader("Authorization") String token, @RequestBody User user) {

        if (jwtUtil.extractRole(token).equals("ADMIN")) {

            User emailCheck = userService.getUserByEmail(user.getEmail());
            User usernameCheck = userService.getUserByUsername(user.getUsername());

            if (emailCheck != null || usernameCheck != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email or username already exists");
            }

            adminService.createUser(user.getUsername(), user.getEmail(), user.getPassword(), user.getRole());
            return ResponseEntity.ok("User created");

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not an admin");
    }
}
