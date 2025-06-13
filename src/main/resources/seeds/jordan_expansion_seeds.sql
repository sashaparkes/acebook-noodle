
-- New Users
INSERT INTO users (username, enabled, first_name, last_name, profile_pic) VALUES
('avianstuff@yahoo.com', true, 'Avian', 'Schmigiel', '/uploads/user_profile/avian.jpg'),
('sashaparkes1@gmail.com', true, 'Sasha', 'Parkes', '/uploads/user_profile/sasha.png'),
('jordangill@live.co.uk', true, 'Jordan', 'Gill', NULL),
('shanice598@yahoo.co.uk', true, 'Shanice', 'Williams', '/uploads/user_profile/shanni.png'),
('harrymcconville1998@gmail.com', true, 'Harry', 'McConville', '/uploads/user_profile/harrymc.jpg');

-- Posts by new users (user_ids 28–32 assumed)
INSERT INTO posts (content, user_id, time_posted, image) VALUES
-- Avian
('Studio all day, beats all night 🧠🎧', 28, NOW() - INTERVAL '2 hours', '/uploads/post_images/39.jpg'),
('Don’t sleep on the quiet ones 🔥', 28, NOW() - INTERVAL '2 days', '/uploads/post_images/83.jpg'),
('Mixing emotions into every track 🎶', 28, NOW() - INTERVAL '3 days', NULL),
-- Sasha
('First gig in London! Let’s go! 🇬🇧', 29, NOW() - INTERVAL '1 hour', NULL),
('Coffee, chords, and courage ☕️🎹', 29, NOW() - INTERVAL '1 day', NULL),
('Dream big, sing louder ✨', 29, NOW() - INTERVAL '3 days', NULL),
-- Jordan
('This app is fire 🔥 thanks for the love!', 30, NOW() - INTERVAL '1 hour', NULL),
('Toronto drill meets Manchester grime? 👀', 30, NOW() - INTERVAL '2 days', NULL),
('Good vibes, good music, good people.', 30, NOW() - INTERVAL '4 days', NULL),
-- Shanice
('Studio lights > nightlife 💡', 31, NOW() - INTERVAL '3 hours', NULL),
('My voice, my rules 🎤', 31, NOW() - INTERVAL '1 day', '/uploads/post_images/shanni_post.jpeg'),
('Nothing like singing in the rain 🌧️🎶', 31, NOW() - INTERVAL '2 days', NULL),
-- Harry
('Looped the same beat for 3 hours. Still vibing.', 32, NOW() - INTERVAL '30 minutes', NULL),
('Dropping my first EP soon! 🔥', 32, NOW() - INTERVAL '1 day', NULL),
('Every setback is a setup for a comeback 💪', 32, NOW() - INTERVAL '3 days', NULL);

-- Friendships (bidirectional)
INSERT INTO friends (main_user_id, friend_user_id, friends_since) VALUES
(28, 1, NOW() - INTERVAL '1 year'), (1, 28, NOW() - INTERVAL '1 year'),
(28, 3, NOW() - INTERVAL '2 years'), (3, 28, NOW() - INTERVAL '2 years'),
(28, 4, NOW() - INTERVAL '6 months'), (4, 28, NOW() - INTERVAL '6 months'),
(28, 5, NOW() - INTERVAL '3 months'), (5, 28, NOW() - INTERVAL '3 months'),
(28, 17, NOW() - INTERVAL '8 months'), (17, 28, NOW() - INTERVAL '8 months'),
(28, 6, NOW() - INTERVAL '1.5 years'), (6, 28, NOW() - INTERVAL '1.5 years'),

-- Sasha
(29, 2, NOW() - INTERVAL '1 year'), (2, 29, NOW() - INTERVAL '1 year'),
(29, 5, NOW() - INTERVAL '9 months'), (5, 29, NOW() - INTERVAL '9 months'),
(29, 10, NOW() - INTERVAL '7 months'), (10, 29, NOW() - INTERVAL '7 months'),
(29, 7, NOW() - INTERVAL '1.5 years'), (7, 29, NOW() - INTERVAL '1.5 years'),
(29, 12, NOW() - INTERVAL '3 years'), (12, 29, NOW() - INTERVAL '3 years'),
(29, 16, NOW() - INTERVAL '2 years'), (16, 29, NOW() - INTERVAL '2 years'),

-- Jordan
(30, 3, NOW() - INTERVAL '2 years'), (3, 30, NOW() - INTERVAL '2 years'),
(30, 14, NOW() - INTERVAL '6 months'), (14, 30, NOW() - INTERVAL '6 months'),
(30, 8, NOW() - INTERVAL '1 year'), (8, 30, NOW() - INTERVAL '1 year'),
(30, 9, NOW() - INTERVAL '1 year'), (9, 30, NOW() - INTERVAL '1 year'),
(30, 4, NOW() - INTERVAL '2 years'), (4, 30, NOW() - INTERVAL '2 years'),
(30, 2, NOW() - INTERVAL '1.5 years'), (2, 30, NOW() - INTERVAL '1.5 years'),

-- Shanice
(31, 6, NOW() - INTERVAL '1 year'), (6, 31, NOW() - INTERVAL '1 year'),
(31, 7, NOW() - INTERVAL '2 years'), (7, 31, NOW() - INTERVAL '2 years'),
(31, 12, NOW() - INTERVAL '2 years'), (12, 31, NOW() - INTERVAL '2 years'),
(31, 15, NOW() - INTERVAL '1 year'), (15, 31, NOW() - INTERVAL '1 year'),
(31, 18, NOW() - INTERVAL '6 months'), (18, 31, NOW() - INTERVAL '6 months'),
(31, 24, NOW() - INTERVAL '1.5 years'), (24, 31, NOW() - INTERVAL '1.5 years'),

-- Harry
(32, 1, NOW() - INTERVAL '2 years'), (1, 32, NOW() - INTERVAL '2 years'),
(32, 2, NOW() - INTERVAL '2 years'), (2, 32, NOW() - INTERVAL '2 years'),
(32, 3, NOW() - INTERVAL '3 years'), (3, 32, NOW() - INTERVAL '3 years'),
(32, 5, NOW() - INTERVAL '1 year'), (5, 32, NOW() - INTERVAL '1 year'),
(32, 13, NOW() - INTERVAL '4 years'), (13, 32, NOW() - INTERVAL '4 years'),
(32, 17, NOW() - INTERVAL '2 years'), (17, 32, NOW() - INTERVAL '2 years');

INSERT INTO comments (post_id, user_id, content, created_at) VALUES
(88, 10, 'Respect from XO 🦉', NOW() - INTERVAL '1 hour'),
(89, 13, 'Queen Bey approves 👑', NOW() - INTERVAL '45 minutes'),
(90, 15, 'Fenty ears are pleased 🎧', NOW() - INTERVAL '30 minutes'),
(90, 30, 'CTRL-alt-repeat 🔁', NOW() - INTERVAL '50 minutes'),
(90, 27, 'Barbz vibe with this 💅', NOW() - INTERVAL '55 minutes'),
(82, 14, 'That grime line hits 🔥 Respect.', NOW() - INTERVAL '30 minutes'),
(90, 1, 'Big talent. Keep rising! 🌟', NOW() - INTERVAL '1 hour'),
(84, 2, 'Grime meets soul. Let’s collab? 🎤', NOW() - INTERVAL '2 hours'),
(85, 7, 'Been looping this in the car!', NOW() - INTERVAL '3 hours'),
(86, 6, 'Your vibe’s contagious 🔥', NOW() - INTERVAL '1.5 hours'),
(87, 8, 'Need this on Spotify ASAP 💽', NOW() - INTERVAL '2 hours');

-- Post Likes for Jordan’s Posts (88-90)
INSERT INTO post_likes (user_id, post_id, created_at) VALUES
(1, 89, NOW() - INTERVAL '1 hour'),
(3, 93, NOW() - INTERVAL '45 minutes'),
(5, 85, NOW() - INTERVAL '1 hour'),
(7, 95, NOW() - INTERVAL '30 minutes'), 
(8, 90, NOW() - INTERVAL '40 minutes'),
(6, 82, NOW() - INTERVAL '1 hour'),
(9, 85, NOW() - INTERVAL '50 minutes'),
(10, 86, NOW() - INTERVAL '30 minutes'),
(13, 90, NOW() - INTERVAL '20 minutes'),
(15, 85, NOW() - INTERVAL '40 minutes'),
(19, 91, NOW() - INTERVAL '30 minutes'),
(20, 86, NOW() - INTERVAL '15 minutes'),
(1, 87, NOW() - INTERVAL '1 hour'),
(2, 85, NOW() - INTERVAL '50 minutes'), 
(3, 84, NOW() - INTERVAL '45 minutes'),
(5, 91, NOW() - INTERVAL '1 hour'), 
(7, 84, NOW() - INTERVAL '30 minutes'), 
(8, 84, NOW() - INTERVAL '40 minutes'),
(6, 92, NOW() - INTERVAL '1 hour'), 
(9, 95, NOW() - INTERVAL '50 minutes'), 
(10, 95, NOW() - INTERVAL '30 minutes'),
(13, 93, NOW() - INTERVAL '20 minutes'), 
(15, 95, NOW() - INTERVAL '40 minutes'),
(19, 90, NOW() - INTERVAL '30 minutes'), 
(20, 92, NOW() - INTERVAL '15 minutes'),
(25, 89, NOW() - INTERVAL '20 minutes'), 
(27, 90, NOW() - INTERVAL '10 minutes');

-- Insert Friend Requests for Jordan (some pending, some accepted/rejected)
INSERT INTO friend_requests (requester_id, receiver_id, status, created_at, responded_at) VALUES
(5, 30, 'pending', NOW() - INTERVAL '2 days', NULL), -- Billie -> Adele (pending)
(10, 30, 'pending', NOW() - INTERVAL '1 day', NULL),  -- Weeknd -> Taylor (pending)
(7, 30, 'rejected', NOW() - INTERVAL '1 week', NOW() - INTERVAL '5 days'), -- Olivia -> Justin (rejected)
(11, 30, 'accepted', NOW() - INTERVAL '2 weeks', NOW() - INTERVAL '10 days'), -- Adele -> Dua (accepted but not in friends yet)
(6, 30, 'pending', NOW() - INTERVAL '3 days', NULL); -- Ed -> Weeknd (pending)


-- Notifications for Jordan
INSERT INTO notifications (receiving_user_id, sending_user_id, type, post_id, comment_id, is_read, created_at) VALUES
(30, 1, 'postLike', 89, NULL, true, NOW() - INTERVAL '1 hour'),
(30, 5, 'postLike', 89, NULL, true, NOW() - INTERVAL '50 minutes'),
(30, 6, 'postLike', 90, NULL, false, NOW() - INTERVAL '45 minutes'),
(30, 13, 'postLike', 90, NULL, true, NOW() - INTERVAL '40 minutes'),
(30, 19, 'postLike', 90, NULL, true, NOW() - INTERVAL '1 hour'),
(30, 27, 'postLike', 90, NULL, true, NOW() - INTERVAL '30 minutes'),
(30, 10, 'comment', 89, 34, true, NOW() - INTERVAL '1 hour'),
(30, 13, 'comment', 89, 35, true, NOW() - INTERVAL '2 hours'),
(30, 15, 'comment', 90, 36, false, NOW() - INTERVAL '3 hours'),
(30, 19, 'commentLike', 90, 37, true, NOW() - INTERVAL '1.5 hours'),
(30, 27, 'comment', 90, 38, true, NOW() - INTERVAL '45 minutes'),
(30, 15, 'commentLike', 90, 37, false, NOW() - INTERVAL '30 minutes');
