CREATE TABLE parties
(
    party_id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    party_name        VARCHAR(255) NOT NULL,
    party_description VARCHAR(500),
    party_leader      VARCHAR(255),
    party_focus       VARCHAR(255),
    party_ideology    VARCHAR(255),
    party_logo        VARCHAR(255) NOT NULL DEFAULT 'default.png'

);

CREATE TABLE users
(
    user_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    role     VARCHAR(255) NOT NULL DEFAULT 'USER'
);

CREATE TABLE articles
(
    article_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    summary    VARCHAR(500) NOT NULL,
    content    TEXT         NOT NULL,
    image_path VARCHAR(255),
    author_id  BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE categories
(
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE -- Removed the trailing comma here
);

CREATE TABLE article_categories
(
    article_id  BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (article_id, category_id),                                          -- Ensure each article can only be associated with a category once
    FOREIGN KEY (article_id) REFERENCES articles (article_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories (category_id) ON DELETE CASCADE -- Removed the trailing comma here
);

CREATE TABLE vote
(
    vote_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id  BIGINT NOT NULL,
    party_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    FOREIGN KEY (party_id) REFERENCES parties (party_id),
    UNIQUE (user_id) -- Ensures a user can only vote once
);

CREATE TABLE comments
(
    comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    article_id BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    content    TEXT   NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (article_id) REFERENCES articles (article_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE article_likes
(
    like_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    article_id BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    FOREIGN KEY (article_id) REFERENCES articles (article_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    UNIQUE (article_id, user_id) -- Ensures a user can only like an article once
);

CREATE TABLE Messages
(
    message_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id  BIGINT NOT NULL,
    content    TEXT   NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    theme VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);



