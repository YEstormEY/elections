# comment service

## description
This service is responsible for managing comments.

## Annotations
- `@Service`: specifies that the class is a service.
- `@Autowired`: injects the CommentRepository, ArticleRepository and UserRepository bean into the service.

## Methods

- `public Iterable<Comment> getCommentsByArticleId(Long articleId) `
- - This method returns all the comments for a given article.
- - It takes the articleId as a parameter and sends it to the CommentRepository to get the comments by articleId.

- `public void save(Long userId, Long articleId, String content)`
- - This method saves a comment to the database.
- - It takes the userId, articleId, and content as parameters.
- - It gets the user and article connected to the comment.
- - It creates a new comment object and sets the user, article, and content.
- - It saves the comment to the database.

