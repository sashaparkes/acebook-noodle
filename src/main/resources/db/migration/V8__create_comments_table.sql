DROP TABLE IF EXISTS comments;
CREATE TABLE comments (
                          id bigserial PRIMARY KEY,
                          post_id BIGINT NOT NULL,
                          user_id BIGINT NOT NULL,
                          content TEXT NOT NULL,
                          created_at TIMESTAMP DEFAULT NOW(),

                          FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);