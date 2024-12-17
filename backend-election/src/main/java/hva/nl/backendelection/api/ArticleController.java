package hva.nl.backendelection.api;

import hva.nl.backendelection.dto.ArticleDTO;
import hva.nl.backendelection.dto.CategoryDTO;
import hva.nl.backendelection.model.Article;
import hva.nl.backendelection.model.Category;
import hva.nl.backendelection.model.request.CreateArticleRequest;
import hva.nl.backendelection.service.ArticleService;
import hva.nl.backendelection.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for handling HTTP requests related to articles
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    /**
     * Autowired Article service class for handling article-related operations
     * Autowired JwtUtil class for handling JWT operations
     */
    @Autowired
    private ArticleService articleService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Method to fetch all articles
     * @return ResponseEntity containing a list of ArticleDTOs
     */
    @GetMapping("/getall")
    public ResponseEntity<Iterable<ArticleDTO>> getAllArticles() {
        // Fetch articles from the service layer
        Iterable<Article> articles = articleService.getAllArticles();

        // Convert articles to DTOs
        Iterable<ArticleDTO> articleDTOs = convertToDTO(articles);

        return ResponseEntity.ok(articleDTOs);
    }

    @GetMapping("/category/getall")
    public ResponseEntity<Iterable<CategoryDTO>> getAllCategories() {
        List<Category> categories = articleService.getAllCategories();

        Iterable<CategoryDTO> categoryDTOs = convertCategoryToDTO(categories);


        System.out.println(categoryDTOs);
        return ResponseEntity.ok(categoryDTOs);
    }

    /**
     * Method to fetch an article by its ID
     * @param articleId ID of the article to fetch
     * @return ResponseEntity containing the ArticleDTO of the article
     */
    @GetMapping("/get/{articleId}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long articleId) {
        Article article = articleService.getArticleById(articleId);

        // If the article doesn't exist, return a 400 Bad Request response
        if (article == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Convert the article to an ArticleDTO
        ArticleDTO articleDTO = convertToDTO(article);
        return ResponseEntity.ok(articleDTO);
    }

    /**
     * Method to fetch all articles by a specific author
     * @return ResponseEntity containing a list of ArticleDTOs
     */
    @PostMapping("/create")
    public ResponseEntity<String> createArticle(@RequestHeader("Authorization") String token, @RequestBody CreateArticleRequest createArticleRequest) {

        System.out.println(createArticleRequest.getCategoryId());

        if (jwtUtil.extractRole(token).equals("USER")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not an admin");
        } if (createArticleRequest.getTitle().isEmpty() || createArticleRequest.getSummary().isEmpty() || createArticleRequest.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please fill in all fields");
        } else

        if(jwtUtil.extractRole(token).equals("ADMIN")) {
            jwtUtil.extractUserId(token);
            articleService.createArticle(createArticleRequest.getTitle(), createArticleRequest.getSummary(), createArticleRequest.getContent(), createArticleRequest.getImagePath(), jwtUtil.extractUserId(token), createArticleRequest.getCategoryId());
            return ResponseEntity.ok("Article created");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not an admin");
        }
    }

    /**
     * Method to fetch all articles by a specific category
     * @param categoryId
     * @return articleDTOs
     */
    @GetMapping("/getbycategory/{categoryId}")
    public ResponseEntity<Iterable<ArticleDTO>> getArticlesByCategory(@PathVariable Long categoryId) {
        Iterable<Article> articles = articleService.getArticlesByCategory(categoryId); // Fetch articles by category
        Iterable<ArticleDTO> articleDTOs = convertToDTO(articles);
        return ResponseEntity.ok(articleDTOs); // Return the list of articles
    }

    /**
     * Method to search for articles by a specific keyword
     * @param search
     * @return articleDTOs
     */
    @GetMapping("/search/{search}")
    public ResponseEntity<Iterable<ArticleDTO>> searchArticles(@PathVariable String search) {
        Iterable<Article> articles = articleService.getArticlesBySearch(search); // Search for articles by keyword
        Iterable<ArticleDTO> articleDTOs = convertToDTO(articles);
        return ResponseEntity.ok(articleDTOs); // Return the list of articles
    }

    /**
     * Method to fetch all the likes of an article.
     * @param articleId as a path variable.
     * @return ResponseEntity containing the number of likes.
     */
    @GetMapping("/getlikes/{articleId}")
    public ResponseEntity<Long> getLikesCount(@PathVariable Long articleId) {
        long likeCount = articleService.getArticleLikesCount(articleId);
        return ResponseEntity.ok(likeCount);
    }

    /**
     * Method to like an article.
     * @param token in header.
     * @param articleId as a path variable.
     * @return ResponseEntity containing a message.
     */
    @PostMapping("/like/{articleId}")
    public ResponseEntity<String> likeArticle(@RequestHeader("Authorization") String token, @PathVariable Long articleId) {
        articleService.likeArticle(articleId, jwtUtil.extractUserId(token));
        return ResponseEntity.ok("Article liked");
    }

    /**
     * Method to unlike an article.
     * @param token in header.
     * @param articleId as a path variable.
     * @return ResponseEntity containing a message.
     */
    @PostMapping("/unlike/{articleId}")
    public ResponseEntity<String> unlikeArticle(@RequestHeader("Authorization") String token, @PathVariable Long articleId) {
        System.out.println(jwtUtil.extractUserId(token));
        System.out.println(articleId);
        articleService.unlikeArticle(articleId, jwtUtil.extractUserId(token));
        return ResponseEntity.ok("Article unliked");
    }

    /**
     * Method to check if the user has liked an article.
     * @param token in header.
     * @param articleId as a path variable.
     * @return ResponseEntity containing a boolean.
     */
    @GetMapping("/checklike/{articleId}")
    public ResponseEntity<Boolean> checkLike(@RequestHeader("Authorization") String token, @PathVariable Long articleId) {
        boolean hasLiked = articleService.checkLike(articleId, jwtUtil.extractUserId(token));
        return ResponseEntity.ok(hasLiked);
    }

    /**
     * Method to create a DTO from an Article object
     * @param article Article object containing the updated article data
     * @return ResponseEntity containing a message indicating the result of the update operation
     */
    private ArticleDTO convertToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setArticleId(article.getArticleId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setSummary(article.getSummary());
        articleDTO.setContent(article.getContent());
        articleDTO.setImagePath(article.getImage_path());
        articleDTO.setAuthorId(article.getAuthor_id());
        articleDTO.setCreated_at(article.getCreated_at());
        // Handle categories if needed (uncomment when the CategoryDTO is available)
         List<CategoryDTO> categoryDTOs = article.getCategories().stream()
             .map(category -> new CategoryDTO(category.getCategoryId(), category.getName()))
             .collect(Collectors.toList());
         articleDTO.setCategories(categoryDTOs);
        return articleDTO;
    }

    /**
     * Method to create a list of DTOs from a list of Article objects
     * @param articles List of Article objects to convert
     * @return List of ArticleDTOs
     */
    private Iterable<ArticleDTO> convertToDTO(Iterable<Article> articles) {
        List<ArticleDTO> articleDTOs = new ArrayList<>();

        for (Article article : articles) {
            ArticleDTO articleDTO = convertToDTO(article);
            articleDTOs.add(articleDTO);
        }

        return articleDTOs;
    }

    private Iterable<CategoryDTO> convertCategoryToDTO(Iterable<Category> category) {
        List<CategoryDTO> categoryDTOs = new ArrayList<>();

        for (Category category1 : category) {
            CategoryDTO categoryDTO = new CategoryDTO(category1.getCategoryId(), category1.getName());
            categoryDTOs.add(categoryDTO);
        }
        return categoryDTOs;
    }

}
