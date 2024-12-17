package hva.nl.backendelection.api;

import hva.nl.backendelection.dto.CommentDTO;
import hva.nl.backendelection.model.Comment;
import hva.nl.backendelection.model.request.PostCommentRequest;
import hva.nl.backendelection.service.CommentService;
import hva.nl.backendelection.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * CommentController class
 * This class is used to handle the API requests related to the comments
 *
 * @RestController indicates that this class is a controller.
 * @RequestMapping("/comments") maps HTTP requests to a specific path("/comments").
 */

@RestController
@RequestMapping("/comments")
public class CommentController {

    // Autowire the required services and utilities
    // Use the commentService to save and retrieve comments.
    @Autowired
    private CommentService commentService;

    // Use the JWTUtil to extract user information from the token.
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Get all comments
     * This method is used to get all the comments from the database.
     * The comments are converted to an Iterable of CommentDTOs before returning.
     *
     * @return CommentDTOs
     */
    @GetMapping("/getall")
    public ResponseEntity<Iterable<CommentDTO>> getAllComments() {
        Iterable<Comment> comments = commentService.getAll();
        Iterable<CommentDTO> commentDTOs = convertToDTO(comments);
        return ResponseEntity.ok(commentDTOs);
    }

    /**
     * Get comments by articleId
     * This method is used to get all the comments related to a specific article.
     * The comments are converted to an Iterable of CommentDTOs before returning.
     * @param articleId to get the comments connected to the article.
     * @return an Iterable of CommentDTOs
     */
    @GetMapping("/get/{articleId}")
    public ResponseEntity<Iterable<CommentDTO>> getCommentsByArticleId(@PathVariable Long articleId) {
        Iterable<Comment> comments = commentService.getCommentsByArticleId(articleId);
        Iterable<CommentDTO> commentDTOs = convertToDTO(comments);
        return ResponseEntity.ok(commentDTOs);
    }

    /**
     * Post a comment
     * This method is used to post a comment to a specific article.
     * The comment gets saved in the database.
     *
     * @PostMapping("/post") maps HTTP POST requests to /comments/post.
     *
     * @param token is checked and used to extract the userId.
     * @param postCommentRequest is used to get the content and articleId of the comment.
     * @return ResponseEntity with a message.
     */
    @PostMapping("/post")
    public ResponseEntity<?> postComment(@RequestHeader("Authorization") String token, @RequestBody PostCommentRequest postCommentRequest) {

        if (token == null) {
            return ResponseEntity.badRequest().body("log in please");
        }

        if(postCommentRequest.getContent() == null || postCommentRequest.getContent().isEmpty()) {
            return ResponseEntity.badRequest().body("Content is required");
        }

        if (postCommentRequest.getArticleId() == null || postCommentRequest.getArticleId() == 0) {
            return ResponseEntity.badRequest().body("ArticleId is required");
        }

        commentService.save(jwtUtil.extractUserId(token), postCommentRequest.getArticleId(), postCommentRequest.getContent());
        return ResponseEntity.ok("Comment posted successfully");
    }

    /**
     * Delete a comment
     * This method is used to delete a comment.
     * The commentId is used to find the comment in the database.
     * The userId is extracted from the token and checked if the comment belongs to the user.
     * If the comment belongs to the user, the comment is deleted.
     * @param token is checked and used to extract the userId.
     * @param commentId is used to find the comment in the database.
     * @return ResponseEntity with a message.
     */
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@RequestHeader("Authorization") String token, @PathVariable Long commentId) {
        if (token == null) {
            return ResponseEntity.badRequest().body("log in please");
        }

        Comment comment = commentService.getCommentById(commentId);
        if (comment.getUser().getUserId() != jwtUtil.extractUserId(token)) {
            return ResponseEntity.badRequest().body("You are not allowed to delete this comment");
        }

        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }

    /**
     * Update a comment
     * This method is used to update a specific comment.
     * The comment gets extracted from the database using the commentId.
     * The userId is extracted from the token and checked if the comment belongs to the user.
     * If the comment belongs to the user, the comment is updated.
     *
     * @PostMapping("/update/{commentId}") maps HTTP POST requests to /comments/update/{commentId}.
     *
     * @param token is checked and used to extract the userId.
     * @param commentId is used to find the comment in the database.
     * @param postCommentRequest Is used to get the content of the comment.
     * @return ResponseEntity with a message.
     */

    @PostMapping("/update/{commentId}")
    public ResponseEntity<?> updateComment(@RequestHeader("Authorization") String token, @PathVariable Long commentId, @RequestBody PostCommentRequest postCommentRequest) {
        if (token == null) {
            return ResponseEntity.badRequest().body("log in please");
        }
        if (postCommentRequest.getContent() == null) {
            return ResponseEntity.badRequest().body("Content is required");
        }

        Comment comment = commentService.getCommentById(commentId);
        if (comment.getUser().getUserId() != jwtUtil.extractUserId(token)) {
            return ResponseEntity.badRequest().body("You are not allowed to update this comment");
        }

        System.out.println(postCommentRequest.getContent());

        commentService.editComment(commentId, postCommentRequest.getContent());

        return ResponseEntity.ok("Comment updated successfully");
    }



    // Method to convert Comment to CommentDTO
    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentId(comment.getCommentId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setUserId(comment.getUser().getUserId());
        commentDTO.setUsername(comment.getUser().getUsername());
        commentDTO.setArticleId(comment.getArticle().getArticleId());
        commentDTO.setCreated_at(comment.getCreatedAt());

        return commentDTO;
    }

    // Method to convert an Iterable of Comments to CommentDTOs
    private Iterable<CommentDTO> convertToDTO(Iterable<Comment> comments) {
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for (Comment comment : comments) {
            commentDTOs.add(convertToDTO(comment));
        }
        return commentDTOs;
    }
}
