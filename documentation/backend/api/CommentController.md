# Comment Controller

## description
This controller is responsible for handling all the request related to the comments.

## Annotations
- `@RestController`: specifies that this class is a controller.
- `@RequestMapping("/comments")`: specifies the base URL for all the comment related requests.
- `@Autowired`: injects the CommentService and JwtUtil `Bean` into the controller.

## Methods
- `@Getmapping`: specifies that this method handles GET requests.
- `public ResponseEntity<Iterable<CommentDTO>> getAllComments()`
- - This method returns all the comments in the database.
- - It returns a `ResponseEntity` With an `Iterable<CommentDTO>`.
- - All the comments are getting converted to the `CommentDTO` before returning to the frontend.
- - The method returns a `HttpStatus.OK` status code.

- `@GetMapping("/get/{id}")`: specifies that this method handles GET requests with the URL `/get/{id}`.
- `public ResponseEntity<Iterable<CommentDTO>> getCommentsByArticleId(@PathVariable Long articleId) `
- - This method returns all the comments related to a specific article.
- - It sends the specific articleId retrieved from the URL to the `commentService` to get all the comments related to the article.
- - It returns a `ResponseEntity` With an `Iterable<CommentDTO>`.
- - All the comments are getting converted to the `CommentDTO` before returning to the frontend.
- - The method returns a `HttpStatus.OK` status code.

- `@PostMapping("/post)` this method handles POST requests with the URL `/post`.
- `public ResponseEntity<?> postComment(@RequestHeader("Authorization") String token, @RequestBody PostCommentRequest postCommentRequest)`
- - This method is responsible for posting a comment.
- - It retrieves the user id from the token and sends the comment to the `commentService` to save the comment in the database.
- - if there is no token or the token is invalid, it returns a `HttpStatus.badRequest` status code.
- - if the comment is successfully saved, it returns a `HttpStatus.OK` status code.

- `private CommentDTO convertToDTO(Comment comment) `
- - this method converts a `Comment` object to a `CommentDTO` object.
- - It sets every value of the `CommentDTO` object to the value of the `Comment` object.
- - It returns the `CommentDTO` object.

- `private Iterable<CommentDTO> convertToDTO(Iterable<Comment> comments)`
- - this method converts an `Iterable<Comment>` object to an `Iterable<CommentDTO>` object.
- - It created an new arraylist of `CommentDTO` and adds every `Comment` object to the arrayList using a for loop.
- - It returns `commentDTOs` arrayList.