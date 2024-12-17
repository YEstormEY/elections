# Article DTO

## Description

The article DTO is a class that represents the article data transfer object(DTO).
It's used to transfer article data between the ArticleController(backend) to the ArticleService(Frontend).

## Annotations
- `@Data`: automatically generates getter, setter, equals, hashCode, and toString methods for the fields.
- `@NoArgsConstructor`: generates a no-argument constructor.
- `@AllArgsConstructor`: generates a constructor with all the fields.

## Fields 

- `id`: the id of the article.
- `title`: the title of the article.
- `summary`: the summary of the article.
- `content`: the content of the article.
- `imagePath`: the image path of the article.
- `AuthorId`: the author id of the article.
- `created_at`: the creation date of the article.
- `categories`: A List of categoryDTO's is being created.