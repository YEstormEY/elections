# Article categories model

## description
This model is used to manage the article categories.

## Annotations
- `@Entity`: This annotation is used to specify that the class is an entity.
- `@table(name="article_categories")`: This annotation is used to specify the name of the table in the database.
- `@Getter, @Setter`: These annotations are used to generate the getters and setters for the fields.
- `@NoArgsConstructor`: This annotation is used to generate a no-argument constructor.

## Fields
- `ArticleCategoryId`: This field is used to store the article category id.
- `@EmbeddedId`: This annotation is used to specify that the field is an embedded id. It is used to specify a composite primary key.

- `Article`: This field is used to store the article.
- `@ManyToOne`: This annotation is used to specify the many-to-one relationship between the article and the article category.
- `@MapsId("articleId")`: This annotation is used to specify the field in the embedded id that is used as a foreign key.
- `@JoinColumn(name="article_id")`: This annotation is used to specify the name of the foreign key column in the database.

- `Category`: This field is used to store the category.
- `@ManyToOne`: This annotation is used to specify the many-to-one relationship between the category and the article category.
- `@MapsId("categoryId")`: This annotation is used to specify the field in the embedded id that is used as a foreign key.
- `@JoinColumn(name="category_id")`: This annotation is used to specify the name of the foreign key column in the database.

