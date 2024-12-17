package hva.nl.backendelection.service;

import hva.nl.backendelection.model.*;
import hva.nl.backendelection.repository.ArticleCategoryRepository;
import hva.nl.backendelection.repository.ArticleRepository;
import hva.nl.backendelection.repository.CategoryRepository;
import hva.nl.backendelection.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


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

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

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

    /**
     * Edit an article as an admin
     * @param articleId the id of the article
     * @param content the new content
     * @param title the new title
     * @param summary the new summary
     */
    public void editArticle(Long articleId, String content, String title, String summary, String imagePath, int[] categories) {
        // Validate input fields
        if (articleId == null) {
            throw new IllegalArgumentException("Article ID cannot be null");
        }
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (summary == null || summary.isEmpty()) {
            throw new IllegalArgumentException("Summary cannot be null or empty");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }

        // Retrieve the article
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found"));

        // Update the article's details
        article.setContent(content);
        article.setTitle(title);
        article.setSummary(summary);
        article.setImage_path(imagePath);

        // Save the updated article
        articleRepository.save(article);

        System.out.println("Category IDs: " + Arrays.toString(categories));

        // Clear existing ArticleCategories relationships for this article
        List<ArticleCategories> existingRelationships = articleCategoryRepository.findByArticle_ArticleId(article.getArticleId());
        if (existingRelationships != null && !existingRelationships.isEmpty()) {
            articleCategoryRepository.deleteAll(existingRelationships);
        }

        // Add new ArticleCategories relationships
        if (categories != null) {
            for (int categoryId : categories) {
                Category category = categoryRepository.findById((long) categoryId)
                        .orElseThrow(() -> new IllegalArgumentException("Category not found"));

                ArticleCategories articleCategory = new ArticleCategories();
                ArticleCategoryId articleCategoryId = new ArticleCategoryId(article.getArticleId(), category.getCategoryId());

                articleCategory.setId(articleCategoryId); // Set the composite ID
                articleCategory.setArticle(article);      // Set the article reference
                articleCategory.setCategory(category);    // Set the category reference

                System.out.println("Saving articleCategory: " + articleCategory);

                articleCategoryRepository.save(articleCategory); // Save the relationship
            }
        }
    }





    /**
     * Delete an article as an admin
     * @param articleId the id of the article
     */
    public void deleteArticle(Long articleId) {
        Article article = articleRepository.findByarticleId(articleId);
        articleRepository.delete(article);
    }


    public List<User> searchUser(String search) {
        Set<User> resultSet = new HashSet<>();

        // Search by email (partial match)
        resultSet.addAll(userRepository.findByEmailContaining(search));

        // Search by username (partial match)
        resultSet.addAll(userRepository.findByUsernameContaining(search));

        // Attempt to search by user ID (exact match)
        try {
            long userId = Long.parseLong(search);
            resultSet.add(userRepository.findByUserId(userId));
        } catch (NumberFormatException e) {
            // Ignore if search is not numeric
        }

        return new ArrayList<>(resultSet);
    }






}
