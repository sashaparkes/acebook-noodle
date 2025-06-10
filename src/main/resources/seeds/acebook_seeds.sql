-- Seed file for social media database with pop artists
-- Run this after all migrations are complete

-- Clear existing data (in correct order to respect foreign keys)
DELETE FROM comment_likes;
DELETE FROM post_likes;
DELETE FROM notifications;
DELETE FROM comments;
DELETE FROM friend_requests;
DELETE FROM friends;
DELETE FROM posts;
DELETE FROM users;

-- Reset sequences
ALTER SEQUENCE users_id_seq RESTART WITH 1;
ALTER SEQUENCE posts_id_seq RESTART WITH 1;
ALTER SEQUENCE comments_id_seq RESTART WITH 1;
ALTER SEQUENCE notifications_id_seq RESTART WITH 1;
ALTER SEQUENCE friend_requests_id_seq RESTART WITH 1;

-- Insert Users (Pop Artists)
INSERT INTO users (username, enabled, first_name, last_name, profile_pic) VALUES
('taylorswift@swiftmail.com', true, 'Taylor', 'Swift', '/uploads/user_profile/taylor.jpg'),
('arianagrande@grandenotes.com', true, 'Ariana', 'Grande', '/uploads/user_profile/ariana.jpg'),
('justinbieber@beliebers.net', true, 'Justin', 'Bieber', '/uploads/user_profile/justin.jpg'),
('billieeilish@oceaneyes.org', true, 'Billie', 'Eilish', '/uploads/user_profile/billie.jpg'),
('dualipa@levitating.fm', true, 'Dua', 'Lipa', '/uploads/user_profile/dua.jpg'),
('edsheeran@shapeofyou.co', true, 'Ed', 'Sheeran', '/uploads/user_profile/ed.jpg'),
('oliviarodrigo@driverslicense.io', true, 'Olivia', 'Rodrigo', '/uploads/user_profile/olivia.jpg'),
('harrystyles@watermelonsugar.love', true, 'Harry', 'Styles', '/uploads/user_profile/harry.jpg'),
('selenagomez@rarebeauty.email', true, 'Selena', 'Gomez', '/uploads/user_profile/selena.jpg'),
('theweeknd@afterhours.zone', true, 'Abel', 'Tesfaye', '/uploads/user_profile/abel.jpg'),
('adele@helloagain.uk', true, 'Adele', 'Adkins', '/uploads/user_profile/adele.jpg'),
('brunomars@uptownfunk.space', true, 'Bruno', 'Mars', '/uploads/user_profile/bruno.jpg');

-- Insert Posts
INSERT INTO posts (content, user_id, time_posted, image) VALUES
-- Taylor Swift posts
('Just finished writing my 200th song this year 🎵 The stories keep coming!', 1, NOW() - INTERVAL '2 hours', 'taylor_post.jpg'),
('Cats are better than people and I will die on this hill 🐱', 1, NOW() - INTERVAL '1 day', NULL),
('13 is my lucky number for a reason ✨', 1, NOW() - INTERVAL '3 days', NULL),

-- Ariana Grande posts
('thank u, next era was just the beginning 💫', 2, NOW() - INTERVAL '4 hours', NULL),
('My vocal range today: 4 octaves and counting 🎤', 2, NOW() - INTERVAL '2 days', 'ariana_post.jpeg'),
('Donut licking was a mistake but yuh 🍩', 2, NOW() - INTERVAL '1 week', NULL),

-- Justin Bieber posts
('Sorry for all the apologies, but here''s another one 🙏', 3, NOW() - INTERVAL '6 hours', NULL),
('Hailey and I just adopted another puppy! 🐕', 3, NOW() - INTERVAL '1 day', 'justin_post.jpg'),
('Baby baby baby ohhh... still stuck in my head', 3, NOW() - INTERVAL '4 days', NULL),

-- Billie Eilish posts
('wore color today and everyone lost their minds 🌈', 4, NOW() - INTERVAL '3 hours', 'billie_post.jpg'),
('bad guy but make it acoustic 😈', 4, NOW() - INTERVAL '2 days', NULL),
('oversized clothes = maximum comfort level achieved', 4, NOW() - INTERVAL '5 days', NULL),

-- Dua Lipa posts
('Levitating to the studio for another banger 🚀', 5, NOW() - INTERVAL '5 hours', NULL),
('New rules: always dance like nobody''s watching 💃', 5, NOW() - INTERVAL '3 days', 'dua_post.gif'),
('Physical training for the tour starts now 💪', 5, NOW() - INTERVAL '1 week', NULL),

-- Ed Sheeran posts
('Mathematical precision in every melody 🔢🎵', 6, NOW() - INTERVAL '7 hours', NULL),
('Thinking out loud about my next album concept', 6, NOW() - INTERVAL '2 days', NULL),
('Perfect collaboration brewing with someone special 👀', 6, NOW() - INTERVAL '6 days', 'ed_post.jpg'),

-- Olivia Rodrigo posts
('drivers license test: passed ✅ heartbreak songs: unlimited', 7, NOW() - INTERVAL '1 hour', NULL),
('good 4 u if you''re not crying to my music rn', 7, NOW() - INTERVAL '1 day', NULL),
('brutal honesty is my brand and I''m not sorry', 7, NOW() - INTERVAL '4 days', 'olivia_post.jpg'),gti

-- Harry Styles posts
('Watermelon sugar high and loving life 🍉', 8, NOW() - INTERVAL '8 hours', NULL),
('Fine line between fashion and art, I choose both', 8, NOW() - INTERVAL '3 days', 'harry_post.jpg'),
('Treat people with kindness, always ❤️', 8, NOW() - INTERVAL '1 week', NULL),

-- Selena Gomez posts
('Rare moments of self-love hit different 💕', 9, NOW() - INTERVAL '2 hours', NULL),
('Lose you to love me was just the beginning of my journey', 9, NOW() - INTERVAL '2 days', 'selena_post.jpg'),
('Mental health check: we''re all learning and growing 🌱', 9, NOW() - INTERVAL '5 days', NULL),

-- The Weeknd posts
('Blinding lights, but make it emotional 🌟', 10, NOW() - INTERVAL '4 hours', NULL),
('Can''t feel my face when the music hits just right', 10, NOW() - INTERVAL '1 day', NULL),
('After hours creativity is when magic happens ✨', 10, NOW() - INTERVAL '3 days', 'abel_post.jpg'),

-- Adele posts
('Hello, it''s me wondering if you''re ready for album 31 👋', 11, NOW() - INTERVAL '6 hours', NULL),
('Rolling in the deep... thoughts about life and love', 11, NOW() - INTERVAL '4 days', NULL),
('Someone like you is out there, just keep believing 💫', 11, NOW() - INTERVAL '1 week', 'adele_post.jpg'),

-- Bruno Mars posts
('Just the way you are is exactly how you should be ✨', 12, NOW() - INTERVAL '3 hours', NULL),
('Uptown funk vibes all day, every day 🕺', 12, NOW() - INTERVAL '2 days', 'bruno_post.jpg'),
('Count on me to bring the groove to every situation', 12, NOW() - INTERVAL '6 days', NULL);

-- Insert Friend Relationships (bidirectional)
INSERT INTO friends (main_user_id, friend_user_id, friends_since) VALUES
-- Taylor's friendships
(1, 2, NOW() - INTERVAL '2 years'), (2, 1, NOW() - INTERVAL '2 years'), -- Taylor & Ariana
(1, 9, NOW() - INTERVAL '3 years'), (9, 1, NOW() - INTERVAL '3 years'), -- Taylor & Selena
(1, 8, NOW() - INTERVAL '1 year'), (8, 1, NOW() - INTERVAL '1 year'),   -- Taylor & Harry

-- Ariana's additional friendships
(2, 3, NOW() - INTERVAL '4 years'), (3, 2, NOW() - INTERVAL '4 years'), -- Ariana & Justin
(2, 12, NOW() - INTERVAL '6 months'), (12, 2, NOW() - INTERVAL '6 months'), -- Ariana & Bruno

-- Justin's friendships
(3, 6, NOW() - INTERVAL '5 years'), (6, 3, NOW() - INTERVAL '5 years'), -- Justin & Ed
(3, 8, NOW() - INTERVAL '2 years'), (8, 3, NOW() - INTERVAL '2 years'), -- Justin & Harry

-- Billie's friendships
(4, 7, NOW() - INTERVAL '1 year'), (7, 4, NOW() - INTERVAL '1 year'),   -- Billie & Olivia
(4, 5, NOW() - INTERVAL '8 months'), (5, 4, NOW() - INTERVAL '8 months'), -- Billie & Dua

-- More cross-connections
(5, 8, NOW() - INTERVAL '1.5 years'), (8, 5, NOW() - INTERVAL '1.5 years'), -- Dua & Harry
(6, 11, NOW() - INTERVAL '3 years'), (11, 6, NOW() - INTERVAL '3 years'), -- Ed & Adele
(9, 7, NOW() - INTERVAL '6 months'), (7, 9, NOW() - INTERVAL '6 months'), -- Selena & Olivia
(10, 12, NOW() - INTERVAL '2 years'), (12, 10, NOW() - INTERVAL '2 years'); -- Weeknd & Bruno

-- Insert Friend Requests (some pending, some accepted/rejected)
INSERT INTO friend_requests (requester_id, receiver_id, status, created_at, responded_at) VALUES
(4, 11, 'pending', NOW() - INTERVAL '2 days', NULL), -- Billie -> Adele (pending)
(10, 1, 'pending', NOW() - INTERVAL '1 day', NULL),  -- Weeknd -> Taylor (pending)
(7, 3, 'rejected', NOW() - INTERVAL '1 week', NOW() - INTERVAL '5 days'), -- Olivia -> Justin (rejected)
(11, 5, 'accepted', NOW() - INTERVAL '2 weeks', NOW() - INTERVAL '10 days'), -- Adele -> Dua (accepted but not in friends yet)
(6, 10, 'pending', NOW() - INTERVAL '3 days', NULL); -- Ed -> Weeknd (pending)

-- Insert Comments
INSERT INTO comments (post_id, user_id, content, created_at) VALUES
-- Comments on Taylor's posts
(1, 2, 'Your songwriting never ceases to amaze me! 💕', NOW() - INTERVAL '1 hour'),
(1, 9, 'Can''t wait to hear them all! 🎵', NOW() - INTERVAL '45 minutes'),
(2, 8, 'Cats > people, always 😸', NOW() - INTERVAL '12 hours'),
(3, 1, 'Lucky number 13 strikes again! ✨', NOW() - INTERVAL '2 days'),

-- Comments on Ariana's posts
(4, 1, 'thank u, next for teaching us self-love 💫', NOW() - INTERVAL '3 hours'),
(5, 12, 'Those vocals are unmatched! 🎤', NOW() - INTERVAL '1 day'),
(6, 3, 'We all make mistakes, yuh! 😂', NOW() - INTERVAL '6 days'),

-- Comments on Justin's posts
(7, 2, 'Growth looks good on you! 🙏', NOW() - INTERVAL '5 hours'),
(8, 8, 'Puppy pics or it didn''t happen! 🐕', NOW() - INTERVAL '20 hours'),
(9, 6, 'That song will never get old 😄', NOW() - INTERVAL '3 days'),

-- Comments on Billie's posts
(10, 7, 'Color looks amazing on you! 🌈', NOW() - INTERVAL '2 hours'),
(11, 5, 'Acoustic version when?? 😈', NOW() - INTERVAL '1 day'),
(12, 4, 'Comfort over fashion, always! 👌', NOW() - INTERVAL '4 days'),

-- Comments on other posts
(13, 4, 'Levitate me to your next concert! 🚀', NOW() - INTERVAL '4 hours'),
(16, 2, 'Mathematical genius meets musical genius! 🔢🎵', NOW() - INTERVAL '6 hours'),
(19, 1, 'Drivers license to break hearts ✅', NOW() - INTERVAL '30 minutes'),
(22, 9, 'Kindness is everything ❤️', NOW() - INTERVAL '7 hours'),
(25, 8, 'Blinding us with talent as always 🌟', NOW() - INTERVAL '3 hours'),
(28, 6, 'Ready for album 31, always! 👋', NOW() - INTERVAL '5 hours');

-- Insert Post Likes
INSERT INTO post_likes (user_id, post_id, created_at) VALUES
-- Likes on Taylor's posts
(2, 1, NOW() - INTERVAL '1.5 hours'), (9, 1, NOW() - INTERVAL '1 hour'), (8, 1, NOW() - INTERVAL '30 minutes'),
(8, 2, NOW() - INTERVAL '15 hours'), (4, 2, NOW() - INTERVAL '10 hours'), (7, 2, NOW() - INTERVAL '8 hours'),
(2, 3, NOW() - INTERVAL '2 days'), (6, 3, NOW() - INTERVAL '1 day'),

-- Likes on Ariana's posts
(1, 4, NOW() - INTERVAL '3.5 hours'), (3, 4, NOW() - INTERVAL '2 hours'), (12, 4, NOW() - INTERVAL '1 hour'),
(12, 5, NOW() - INTERVAL '1.5 days'), (1, 5, NOW() - INTERVAL '1 day'), (8, 5, NOW() - INTERVAL '12 hours'),
(3, 6, NOW() - INTERVAL '6 days'), (1, 6, NOW() - INTERVAL '5 days'),

-- Likes on other posts
(2, 7, NOW() - INTERVAL '5.5 hours'), (8, 7, NOW() - INTERVAL '4 hours'),
(8, 8, NOW() - INTERVAL '18 hours'), (1, 8, NOW() - INTERVAL '16 hours'), (2, 8, NOW() - INTERVAL '14 hours'),
(7, 10, NOW() - INTERVAL '2.5 hours'), (5, 10, NOW() - INTERVAL '1.5 hours'),
(4, 13, NOW() - INTERVAL '4.5 hours'), (8, 13, NOW() - INTERVAL '3 hours'),
(1, 19, NOW() - INTERVAL '45 minutes'), (9, 19, NOW() - INTERVAL '25 minutes'),
(9, 22, NOW() - INTERVAL '6.5 hours'), (1, 22, NOW() - INTERVAL '5 hours'),
(6, 28, NOW() - INTERVAL '4.5 hours'), (5, 28, NOW() - INTERVAL '3 hours');

-- Insert Comment Likes
INSERT INTO comment_likes (user_id, comment_id, created_at) VALUES
-- Likes on comments
(1, 1, NOW() - INTERVAL '50 minutes'), (8, 1, NOW() - INTERVAL '40 minutes'),
(1, 2, NOW() - INTERVAL '35 minutes'), (2, 2, NOW() - INTERVAL '25 minutes'),
(1, 3, NOW() - INTERVAL '10 hours'), (2, 3, NOW() - INTERVAL '8 hours'),
(2, 4, NOW() - INTERVAL '1.5 days'), (9, 4, NOW() - INTERVAL '1 day'),
(2, 5, NOW() - INTERVAL '2.5 hours'), (3, 5, NOW() - INTERVAL '2 hours'),
(1, 6, NOW() - INTERVAL '22 hours'), (2, 6, NOW() - INTERVAL '20 hours'),
(1, 7, NOW() - INTERVAL '4.5 hours'), (8, 7, NOW() - INTERVAL '3 hours'),
(3, 8, NOW() - INTERVAL '18 hours'), (1, 8, NOW() - INTERVAL '16 hours'),
(4, 9, NOW() - INTERVAL '1.5 hours'), (5, 9, NOW() - INTERVAL '1 hour'),
(7, 10, NOW() - INTERVAL '45 minutes'), (8, 10, NOW() - INTERVAL '30 minutes');

-- Insert Notifications
INSERT INTO notifications (receiving_user_id, sending_user_id, type, post_id, comment_id, is_read, created_at) VALUES
-- Post like notifications
(1, 2, 'post_like', 1, NULL, true, NOW() - INTERVAL '1.5 hours'),
(1, 9, 'post_like', 1, NULL, false, NOW() - INTERVAL '1 hour'),
(1, 8, 'post_like', 2, NULL, false, NOW() - INTERVAL '15 hours'),
(2, 1, 'post_like', 4, NULL, true, NOW() - INTERVAL '3.5 hours'),
(2, 12, 'post_like', 5, NULL, false, NOW() - INTERVAL '1.5 days'),

-- Comment notifications
(1, 2, 'comment', 1, 1, true, NOW() - INTERVAL '1 hour'),
(1, 9, 'comment', 1, 2, false, NOW() - INTERVAL '45 minutes'),
(1, 8, 'comment', 2, 3, false, NOW() - INTERVAL '12 hours'),
(2, 1, 'comment', 4, 4, true, NOW() - INTERVAL '3 hours'),
(3, 8, 'comment', 8, 8, false, NOW() - INTERVAL '20 hours'),

-- Comment like notifications
(2, 1, 'comment_like', 1, 1, true, NOW() - INTERVAL '50 minutes'),
(9, 1, 'comment_like', 1, 2, false, NOW() - INTERVAL '35 minutes'),
(8, 1, 'comment_like', 2, 3, false, NOW() - INTERVAL '10 hours'),

-- Friend request notifications
(11, 4, 'friend_request', NULL, NULL, false, NOW() - INTERVAL '2 days'),
(1, 10, 'friend_request', NULL, NULL, false, NOW() - INTERVAL '1 day'),
(3, 7, 'friend_request', NULL, NULL, true, NOW() - INTERVAL '1 week'),
(5, 11, 'friend_request', NULL, NULL, true, NOW() - INTERVAL '2 weeks'),
(10, 6, 'friend_request', NULL, NULL, false, NOW() - INTERVAL '3 days');

-- Summary of seeded data
SELECT 
    'Data Summary' as info,
    (SELECT COUNT(*) FROM users) as total_users,
    (SELECT COUNT(*) FROM posts) as total_posts,
    (SELECT COUNT(*) FROM comments) as total_comments,
    (SELECT COUNT(*) FROM friends) as total_friendships,
    (SELECT COUNT(*) FROM friend_requests) as total_friend_requests,
    (SELECT COUNT(*) FROM post_likes) as total_post_likes,
    (SELECT COUNT(*) FROM comment_likes) as total_comment_likes,
    (SELECT COUNT(*) FROM notifications) as total_notifications;