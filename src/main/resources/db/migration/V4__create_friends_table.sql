DROP TABLE IF EXISTS friends;

CREATE TABLE friends (
   main_user_id bigserial,
    friend_user_id bigserial,
    PRIMARY KEY(main_user_id, friend_user_id)

);