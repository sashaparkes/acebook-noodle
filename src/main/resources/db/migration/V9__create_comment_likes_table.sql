

CREATE TABLE comment_likes (
                               user_id BIGINT NOT NULL,
                               comment_id BIGINT NOT NULL,
                               created_at TIMESTAMP DEFAULT NOW(),

                               PRIMARY KEY (user_id, comment_id),

                               FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                               FOREIGN KEY (comment_id) REFERENCES comments(id) ON DELETE CASCADE
);
