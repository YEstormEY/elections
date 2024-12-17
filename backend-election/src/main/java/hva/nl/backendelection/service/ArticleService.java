package hva.nl.backendelection.service;

import hva.nl.backendelection.model.*;
import hva.nl.backendelection.repository.ArticleCategoryRepository;
import hva.nl.backendelection.repository.ArticleRepository;
import hva.nl.backendelection.repository.CategoryRepository;
import hva.nl.backendelection.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    @Autowired
    private LikeRepository likeRepository;


    public Iterable<Article> getAllArticles() {return articleRepository.findAll();}

    public List<Category> getAllCategories() {
        // Convert Iterable<Category> to List<Category>
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);

        return categories;  // Return the list of categories
    }


    public Article getArticleById(Long articleId) {
        return articleRepository.findByarticleId(articleId);
    }

    public void createArticle(String title, String summary, String content, String imagePath, Long userId, List<Long> categoryIds) {
        // Create and save the Article
        Article article = new Article();
        article.setTitle(title);
        article.setSummary(summary);
        article.setContent(content);
        article.setImage_path(imagePath);
        article.setAuthor_id(userId);
        articleRepository.save(article);

        // Retrieve each Category by ID and create an ArticleCategories link
        for (Long categoryId : categoryIds) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));

            ArticleCategories articleCategory = new ArticleCategories();
            ArticleCategoryId articleCategoryId = new ArticleCategoryId(article.getArticleId(), category.getCategoryId());

            articleCategory.setId(articleCategoryId); // Set the composite ID
            articleCategory.setArticle(article);      // Set the article reference
            articleCategory.setCategory(category);    // Set the category reference

            articleCategoryRepository.save(articleCategory); // Save the relationship
        }
    }

    public Long getArticleLikesCount(Long articleId) {
        return likeRepository.countByArticle_articleId(articleId);
    }

    public void likeArticle(Long articleId, Long userId) {
        // Check if the user has already liked the article
        if (likeRepository.countByArticleIdAndUserId(articleId, userId) >= 1) {
            throw new IllegalArgumentException("User has already liked this article");
        }

        // Retrieve the Article and User objects
        Article article = articleRepository.findByarticleId(articleId);
        User user = new User();
        user.setUserId(userId);

        // Create and save the ArticleLikes object
        ArticleLikes articleLikes = new ArticleLikes();
        articleLikes.setArticle(article);
        articleLikes.setUser(user);
        likeRepository.save(articleLikes);
    }

    public boolean checkLike(Long articleId, Long userId) {
        return likeRepository.countByArticleIdAndUserId(articleId, userId) >= 1;
    }

    public void unlikeArticle(Long articleId, Long userId) {
        // Retrieve the ArticleLikes object
        ArticleLikes articleLikes = likeRepository.getByArticle_articleIdAndUser_userId(articleId, userId);

        Long likeId = articleLikes.getLikeId();
        // Delete the ArticleLikes object
        likeRepository.deleteById(likeId);
    }
}
