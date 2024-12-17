# ArticleController

## Description
The article controller is a class that handles requests related to articles.

## Annotations
- `@RestController`: specifies that the class is a controller.
- `@RequestMapping`: specifies the base URL for the controller`/article`.

## dependencies
- `ArticleService`: provides methods related to article management, such as creating and getting articles.
- `JwtUtil`: provides methods for generating and validating JWT tokens.

## Endpoints

### `GET /getall`

- URL: `/article/getall`
- Method: `GET`
- @Return
  - `articleDtos`: A Iterable of all articles is converted to a list of `ArticleDto`.
- logic:
  - Retrieves all articles from the database using the `ArticleService`.
  - Converts the articles to a list of `ArticleDto` to hide sensitive information.
  - Returns the list of `ArticleDtos`.

### `GET /get/{articleId}`
- URL: `/article/get/{articleId}`
- Mehtod: `GET`
- @Param
  - `articleId`: The id of the article to retrieve.
- @Return
  - `ArticleDto`: The article with the specified id.
- logic:
  - Retrieves the article with the specified id from the database using the `ArticleService`.
  - Converts the article to an `ArticleDto` so the frontend receives a valid JSON.
  - Returns the `ArticleDto`.

### `POST /create`
- URL: `/article/create`
- Mehtod: `POST`
- RequestHeader:
  - `Authorization`: The JWT token for authentication.
- RequestBody:
  - `title`: The title of the article.
  - `summary`: The summary of the article.
  - `content`: The content of the article.
  - `imagePath`: The path to the image of the article.

### `GET /getlikes/{articleId}`
- URL: `/article/getlikes/{articleId}`
- Method: `GET`
- @Param
  - `articleId`: The id of the article to retrieve the likes.
- @Return
  - `Long`: The number of likes for the article.

### `POST /like/{articleId}`
- URL: `/article/like/{articleId}`
- Method: `POST`
- RequestHeader
- `Authorization`: The JWT token for authentication.
- @Param
  - `articleId`: The id of the article to like.
- logic:
  - retrieves the userId from the JWT token using the `JwtUtil`.
  - likes the article with the specified id using the `ArticleService`.
- @Return
  - `String`: A message indicating that the article was liked.

### `POST /unlike/{articleId}`
- URL: `/article/unlike/{articleId}`
- Method: `POST`
- RequestHeader
  - `Authorization`: The JWT token for authentication.
  - @Param
    - `articleId`: The id of the article to unlike.
  - logic:
    - retrieves the userId from the JWT token using the `JwtUtil`.
    - unlikes the article with the specified id using the `ArticleService`.
- @Return
  - `String`: A message indicating that the article was unliked.

### `GET /checklike/{articleId}`
- URL: `/article/checklike/{articleId}`
- Method: `GET`
- RequestHeader
  - `Authorization`: The JWT token for authentication.
  - @Param
    - `articleId`: The id of the article to check if it is liked.
  - logic:
    - retrieves the userId from the JWT token using the `JwtUtil`.
    - checks if the article with the specified id is liked by tge user using the `ArticleService`.
- @Return
- `Boolean`: A boolean value indicating if the article is liked by the user.


## methods

### `ArticleDto convertToDto(Article article)`
- Converts an `Article` entity to an `ArticleDto` object.
- @Param
  - `article`: The article entity to convert.
- @Return
  - `ArticleDto`: The converted `ArticleDto` object.
- logic:
  - Creates a new `ArticleDto` object.
  - Copies the fields from the `Article` entity to the `ArticleDto`.
  - Sets the category name in the `ArticleDto`.
  - Returns the `ArticleDto`.

### `Iterable<ArticleDTO> convertToDTO(Iterable<Article> articles)`
- Converts an Iterable of `Article` entities to a list of `ArticleDto` objects.
- @Param
  - `articles`: The Iterable of articles to convert.
- @Return
  - `List<ArticleDTO> articleDTOs`: The list of converted `ArticleDto` objects.
- logic:
  - Creates an empty list of `ArticleDto`.
  - Iterates over the articles and converts each one to an `ArticleDto`.
  - Adds the converted `ArticleDto` to the list.
  - Returns the list of `ArticleDTOs`.