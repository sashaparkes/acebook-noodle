DROP TABLE IF EXISTS friend_requests;

CREATE TABLE friend_requests (
                                 id BIGSERIAL PRIMARY KEY,
                                 requester_id BIGINT NOT NULL,
                                 receiver_id BIGINT NOT NULL,
                                 status TEXT NOT NULL DEFAULT 'pending',-- 'pending', 'accepted', 'rejected'
                                 created_at TIMESTAMP DEFAULT NOW(),
                                 responded_at TIMESTAMP,

                                 FOREIGN KEY (requester_id) REFERENCES users(id) ON DELETE CASCADE,
                                 FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE,

                                 CHECK (requester_id <> receiver_id),
                                 CHECK (status IN ('pending', 'accepted', 'rejected')),

                                 UNIQUE (requester_id, receiver_id)     -- prevent duplicate requests
);
