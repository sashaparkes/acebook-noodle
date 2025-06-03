DROP TABLE IF EXISTS postLikes;

CREATE TABLE post_likes (
                            user_id bigserial NOT NULL,
                            post_id bigserial NOT NULL,
                            created_at TIMESTAMP DEFAULT NOW(),

                            PRIMARY KEY (user_id, post_id),

                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);
