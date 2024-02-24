CREATE TABLE users (
        id SERIAL PRIMARY KEY,
        email VARCHAR(255),
        password VARCHAR(255),
        bio VARCHAR(255),
        image VARCHAR(255),
        username VARCHAR(255)
);

CREATE TABLE articles (
        id SERIAL PRIMARY KEY,
        slug VARCHAR(255),
        title VARCHAR(255),
        description VARCHAR(255),
        body TEXT,
        author_id BIGINT,
        createdAt TIMESTAMP,
        updatedAt TIMESTAMP,
        FOREIGN KEY (author_id) REFERENCES users(id)
);

CREATE TABLE comments (
        id SERIAL PRIMARY KEY,
        body TEXT,
        author_id BIGINT,
        article_id BIGINT,
        createdAt TIMESTAMP,
        updatedAt TIMESTAMP,
        FOREIGN KEY (author_id) REFERENCES users(id),
        FOREIGN KEY (article_id) REFERENCES articles(id)
);

CREATE TABLE tags (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255)
);

CREATE TABLE favorites (
        user_id BIGINT,
        article_id BIGINT,
        PRIMARY KEY (user_id, article_id),
        FOREIGN KEY (user_id) REFERENCES users(id),
        FOREIGN KEY (article_id) REFERENCES articles(id)
);


CREATE TABLE user_followers (
        user_id BIGINT,
        follower_id BIGINT,
        PRIMARY KEY (user_id, follower_id),
        FOREIGN KEY (user_id) REFERENCES users(id),
        FOREIGN KEY (follower_id) REFERENCES users(id)
);

CREATE TABLE article_tags (
        article_id BIGINT,
        tag_id BIGINT,
        PRIMARY KEY (article_id, tag_id),
        FOREIGN KEY (article_id) REFERENCES articles(id),
        FOREIGN KEY (tag_id) REFERENCES tags(id)
);
