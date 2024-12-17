# Article Service

The Article Service is responsible for the connection between the frontend and the backend for all operations related to the articles.

## `Methods`

### `export async function fetchArticles()`

- Description: Fetches all articles from the backend.
- @Return
  - `Article[]`: An array of all articles.
- Logic:
  - Sends a GET request to the `/articles` endpoint.
  - Returns the response data.

### `export async function fetchArticleById(articleId)`

- Description: Fetches an article by its ID from the backend.
- @Param
  - `articleId`: The ID of the article to fetch the article connected to the id.
- @Return
  - `Article`: The article with the specified ID.
- logic:
  - Sends a GET request to the `/articles/{articleId}` endpoint.
  - Returns the response data.