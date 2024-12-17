package hva.nl.backendelection.service;

import hva.nl.backendelection.model.User;
import hva.nl.backendelection.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This class is responsible for handling the business logic of the User entity.
 * It is used to register and login users.
 */
@Service
public class UserService {


    /**
     * The UserRepository class is used to perform CRUD operations on the User entity.
     * The PasswordEncoder class is used to encrypt and decrypt passwords.
     */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password, String email) {
        if (userRepository.findByUsername(username) != null && userRepository.findByEmail(username) != null) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));  // Encrypt the password
        user.setEmail(email);
        user.setRole("USER");
        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return user;
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserById(Long userId) {
        return userRepository.findByUserId(userId);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional // Ensure that all operations in this method are part of the same transaction
    public void deleteUser(Long userId) {
        // Fetch and delete the user
        User user = userRepository.findByUserId(userId);
        userRepository.delete(user);
    }

    public User updateUser(Long userId, String username, String email) {
        System.out.println(username + userId);

        User user = userRepository.findByUserId(userId);

        System.out.println(user);

        user.setUsername(username);
        user.setEmail(email);

        userRepository.save(user);
        return user;
    }

    public User updatePassword(Long userId, String password, String newPassword) {
        User user = userRepository.findByUserId(userId);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return user;
    }
}
