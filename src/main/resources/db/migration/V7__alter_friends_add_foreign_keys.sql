

DROP TABLE IF EXISTS friends;

CREATE TABLE friends (
                         main_user_id bigserial NOT NULL,
                         friend_user_id bigserial NOT NULL,
                         PRIMARY KEY (main_user_id, friend_user_id),

                         FOREIGN KEY (main_user_id) REFERENCES users(id) ON DELETE CASCADE,
                         FOREIGN KEY (friend_user_id) REFERENCES users(id) ON DELETE CASCADE,

                         CHECK (main_user_id <> friend_user_id)
);
