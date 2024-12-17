package hva.nl.backendelection.service;

import hva.nl.backendelection.model.Article;
import hva.nl.backendelection.model.Comment;
import hva.nl.backendelection.model.User;
import hva.nl.backendelection.repository.ArticleRepository;
import hva.nl.backendelection.repository.CommentRepository;
import hva.nl.backendelection.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + commentId));
    }


    public Iterable<Comment> getCommentsByArticleId(Long articleId) {
        return commentRepository.findByArticle_ArticleId(articleId);
    }

    public Iterable<Comment> getAll() {
        return commentRepository.findAll();
    }

    public void save(Long userId, Long articleId, String content) {
        // Find the Article by its ID
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found with ID: " + articleId));

        // Find the User by their ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Create a new Comment
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setArticle(article);
        comment.setUser(user);

        // Save the Comment to the database
        commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public void editComment(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + commentId));
        comment.setContent(content);
        commentRepository.save(comment);
    }
}
