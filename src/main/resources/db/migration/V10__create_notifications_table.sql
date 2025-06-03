DROP TABLE IF EXISTS notifications;

CREATE TABLE notifications (
                               id BIGSERIAL PRIMARY KEY,
                               receiving_user_id BIGINT NOT NULL,
                               sending_user_id BIGINT,
                               type TEXT NOT NULL,
                               post_id BIGINT,                         -- means clicking on this could take you to related post
                               comment_id BIGINT,                      --  same but for comment.
                               is_read BOOLEAN DEFAULT FALSE,
                               created_at TIMESTAMP DEFAULT NOW(),

                               FOREIGN KEY (receiving_user_id) REFERENCES users(id) ON DELETE CASCADE,
                               FOREIGN KEY (sending_user_id) REFERENCES users(id) ON DELETE SET NULL,
                               FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
                               FOREIGN KEY (comment_id) REFERENCES comments(id) ON DELETE CASCADE
);
