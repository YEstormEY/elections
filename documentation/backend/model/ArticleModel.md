# Article Model

## Description
The article model is a class that represents the article table in the database.

## Annotations
- `@Entity`: specifies that the class is an entity.
- `@Getter, @Setter`: automatically generates getter and setter methods for the fields.
- `@NoArgsConstructor`: generates a no-argument constructor.
- `@Table(name="articles")`: specifies the name of the table in the database.

## Fields
- `articleId`: the id of the article.
- - the `@Id` annotation specifies that this field is the primary key.
- - the `@Column(name="article_id")` annotation specifies the name of the column in the database.
- - the `@GeneratedValue(strategy=GenerationType.IDENTITY)` annotation specifies that the value of this field is generated automatically.

- `title`: the title of the article.
- - the `@Column` annotation specifies that this field is a column in the database.

- `summary`: the summary of the article.
- - the `@Column` annotation specifies that this field is a column in the database.

- `content`: the content of the article.
- - the `@Column` annotation specifies that this field is a column in the database.

- `image_path`: the path to the image of the article.
- - the `@Column` annotation specifies the name of the column in the database.

- `author_id`: the id of the author of the article.
- - the `@Column` annotation specifies the name of the column in the database.

- `created_at`: the date and time when the article was created.
- - the `@Column` annotation specifies that this field is a column in the database.

- `updated_at`: the date and time when the article was last updated.
- - the `@Column` annotation specifies that this field is a column in the database.

- `categories`: the categories associated with the article.
- - the `@ManyToMany` annotation specifies the relationship between the article and the categories. an article can have multiple categories.
- - the `@JoinTable` annotation specifies the join table for the many-to-many relationship.

