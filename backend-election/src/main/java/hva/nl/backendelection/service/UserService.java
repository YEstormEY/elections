package hva.nl.backendelection.service;

import hva.nl.backendelection.model.User;
import hva.nl.backendelection.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Autowired
    private JavaMailSender mailSender;

    public User registerUser(String username, String password, String email) {
        if (userRepository.findByUsername(username) != null && userRepository.findByEmail(username) != null) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));  // Encrypt the password
        user.setEmail(email);
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        // Send the welcome email after saving the user
        sendWelcomeEmail(savedUser);

        return savedUser;
    }

    private void sendWelcomeEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Welkom bij NextGenPolitics, " + user.getUsername() + "!");
        message.setText(
                "Hallo " + user.getUsername() + ",\n\n" +
                        "Welkom bij NextGenPolitics! We zijn enthousiast dat je ons platform hebt gekozen om je politieke kennis te verrijken. Hier is wat je kunt ontdekken:\n\n" +
                        "- 📚 Inzichtelijke artikelen en uitleg over politiek en democratie.\n" +
                        "- 🎥 Video's en interactieve quizzen om politieke concepten te begrijpen.\n" +
                        "- 🗳️ Informatie over hoe je je stem kunt laten horen en impact kunt maken.\n\n" +
                        "Ons doel is om de volgende generatie politiek actief te maken. Neem een kijkje op onze populairste pagina’s:\n" +
                        "- 📖 Politieke structuur: [LINK]\n" +
                        "- 🎉 Verkiezingen uitgelegd: [LINK]\n" +
                        "- 🌱 Klimaat en toekomst: [LINK]\n\n" +
                        "Volg ons op sociale media voor regelmatige updates:\n" +
                        "- 📸 Instagram: @NextGenPolitics\n" +
                        "- 🐦 Twitter: @NextGenPolitics\n" +
                        "- 🎵 TikTok: @NextGenPolitics\n\n" +
                        "We hopen dat je een geweldige ervaring hebt op ons platform.\n\n" +
                        "Met vriendelijke groet,\n" +
                        "Het NextGenPolitics Team"
        );

        mailSender.send(message);
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
