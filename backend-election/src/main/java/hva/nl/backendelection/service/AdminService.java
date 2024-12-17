package hva.nl.backendelection.service;

import hva.nl.backendelection.model.User;
import hva.nl.backendelection.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * The AdminService class is used to handle the business logic of the AdminController class.
 */
@Service
public class AdminService {

    /**
     * The UserRepository class is used to access the user data in the database.
     * The PasswordEncoder class is used to encode the password of the user.
     */

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Get all users
     * @return all users found in the userRepository
     */

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get a user by userId
     * @param userId the id of the user
     */

    public void deleteUser(Long userId) {
        User user = userRepository.findByUserId(userId);
        userRepository.delete(user);
    }

    /**
     * Update a user
     * @param userId the id of the user
     * @param username the new username
     * @param email the new email
     * @param role the new role
     * @return the updated user
     */

    public User updateUser(Long userId, String username, String email, String role) {
        User user = userRepository.findByUserId(userId);
        user.setUsername(username);
        user.setEmail(email);
        user.setRole(role);
        userRepository.save(user);
        return user;
    }

    /**
     * Create a new user
     * @param username the username
     * @param email the email
     * @param password the password
     * @param role the role
     */

    public void createUser(String username, String email, String password, String role) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
