# Comment DTO

## description
This DTO is used to return a comment object to the frontend.

## Annotation
- `@Data` : Lombok annotation to generate getters and setters for all fields.
- `@NoArgsConstructor` : Lombok annotation to generate a no-argument constructor.
- `@AllArgsConstructor` : Lombok annotation to generate a constructor with all fields.

## Fields
- `commentId` : the id of the comment.
- `content` : the content of the comment.
- `articleId` : the id of the article that the comment belongs to.
- `userId` : the id of the user who posted the comment.
- `username` : the username of the user who posted the comment.
- `createdAt` : the date and time when the comment was posted.
