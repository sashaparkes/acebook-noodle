ALTER TABLE posts
ADD user_id bigserial,
ADD CONSTRAINT post_user_id
FOREIGN KEY (user_id) REFERENCES users(id),
ADD time_posted timestamp;