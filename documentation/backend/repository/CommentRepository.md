# Comment Repository

## Description
the comment repository is an interface that extends the `CrudRepository` interface. It provides methods to interact with the comment table in the database.

## Methods
- `Iterable<Comment> findByArticle_ArticleId(Long articleId);`
- - finds all comments by article id.
- - `articleId`: the id of the article.
- - returns an iterable of comments.