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
('brunomars@uptownfunk.space', true, 'Bruno', 'Mars', '/uploads/user_profile/bruno.jpg'),
('beyonce@queenbey.com', true, 'Beyoncé', 'Knowles', '/uploads/user_profile/beyonce.jpeg'),
('drake@ovo.ca', true, 'Aubrey', 'Graham', '/uploads/user_profile/drake.jpeg'),
('rihanna@fentyworld.com', true, 'Rihanna', 'Fenty', '/uploads/user_profile/rihanna.jpg'),
('ladygaga@monstermail.net', true, 'Lady', 'Gaga', '/uploads/user_profile/gaga.jpg'),
('khalid@location.live', true, 'Khalid', '', '/uploads/user_profile/khalid.jpeg'),
('lizzo@juice.fm', true, 'Lizzo', '', '/uploads/user_profile/lizzo.jpeg'),
('sza@ctrl.zone', true, 'SZA', '', '/uploads/user_profile/sza.jpg'),
('shawnmendes@stitches.ca', true, 'Shawn', 'Mendes', '/uploads/user_profile/shawn.jpg'),
('camila@havanamail.cu', true, 'Camila', 'Cabello', '/uploads/user_profile/camila.jpg'),
('charlieputh@attention.co', true, 'Charlie', 'Puth', '/uploads/user_profile/charlie.jpg'),
('bts@bangtan.kr', true, 'BTS', '', '/uploads/user_profile/bts.jpeg'),
('zayn@pillowtalk.uk', true, 'Zayn', 'Malik', '/uploads/user_profile/zayn.jpg'),
('halsey@badlands.com', true, 'Halsey', '', '/uploads/user_profile/halsey.jpg'),
('dojacat@planether.space', true, 'Doja', 'Cat', '/uploads/user_profile/doja.jpg'),
('nickiminaj@barbz.net', true, 'Nicki', 'Minaj', '/uploads/user_profile/nicki.jpg');

-- Insert Posts
INSERT INTO posts (content, user_id, time_posted, image) VALUES
-- Taylor Swift posts
('Just finished writing my 200th song this year 🎵 The stories keep coming!', 1, NOW() - INTERVAL '2 hours', '/uploads/post_images/taylor_post.jpg'),
('Cats are better than people and I will die on this hill 🐱', 1, NOW() - INTERVAL '1 day', NULL),
('13 is my lucky number for a reason ✨', 1, NOW() - INTERVAL '3 days', NULL),

-- Ariana Grande posts
('thank u, next era was just the beginning 💫', 2, NOW() - INTERVAL '4 hours', NULL),
('My vocal range today: 4 octaves and counting 🎤', 2, NOW() - INTERVAL '2 days', '/uploads/post_images/ariana_post.jpeg'),
('Donut licking was a mistake but yuh 🍩', 2, NOW() - INTERVAL '1 week', NULL),

-- Justin Bieber posts
('Sorry for all the apologies, but here''s another one 🙏', 3, NOW() - INTERVAL '6 hours', NULL),
('Hailey and I just adopted another puppy! 🐕', 3, NOW() - INTERVAL '1 day', '/uploads/post_images/justin_post.jpg'),
('Baby baby baby ohhh... still stuck in my head', 3, NOW() - INTERVAL '4 days', NULL),

-- Billie Eilish posts
('wore color today and everyone lost their minds 🌈', 4, NOW() - INTERVAL '3 hours', '/uploads/post_images/billie_post.jpg'),
('bad guy but make it acoustic 😈', 4, NOW() - INTERVAL '2 days', NULL),
('oversized clothes = maximum comfort level achieved', 4, NOW() - INTERVAL '5 days', NULL),

-- Dua Lipa posts
('Levitating to the studio for another banger 🚀', 5, NOW() - INTERVAL '5 hours', NULL),
('New rules: always dance like nobody''s watching 💃', 5, NOW() - INTERVAL '3 days', '/uploads/post_images/dua_post.jpg'),
('Physical training for the tour starts now 💪', 5, NOW() - INTERVAL '1 week', NULL),

-- Ed Sheeran posts
('Mathematical precision in every melody 🔢🎵', 6, NOW() - INTERVAL '7 hours', NULL),
('Thinking out loud about my next album concept', 6, NOW() - INTERVAL '2 days', NULL),
('Perfect collaboration brewing with someone special 👀', 6, NOW() - INTERVAL '6 days', '/uploads/post_images/ed_post.jpg'),

-- Olivia Rodrigo posts
('drivers license test: passed ✅ heartbreak songs: unlimited', 7, NOW() - INTERVAL '1 hour', NULL),
('good 4 u if you''re not crying to my music rn', 7, NOW() - INTERVAL '1 day', NULL),
('brutal honesty is my brand and I''m not sorry', 7, NOW() - INTERVAL '4 days', '/uploads/post_images/olivia_post.jpg'),

-- Harry Styles posts
('Watermelon sugar high and loving life 🍉', 8, NOW() - INTERVAL '8 hours', NULL),
('Fine line between fashion and art, I choose both', 8, NOW() - INTERVAL '3 days', '/uploads/post_images/harry_post.jpg'),
('Treat people with kindness, always ❤️', 8, NOW() - INTERVAL '1 week', NULL),

-- Selena Gomez posts
('Rare moments of self-love hit different 💕', 9, NOW() - INTERVAL '2 hours', NULL),
('Lose you to love me was just the beginning of my journey', 9, NOW() - INTERVAL '2 days', '/uploads/post_images/selena_post.png'),
('Mental health check: we''re all learning and growing 🌱', 9, NOW() - INTERVAL '5 days', NULL),

-- The Weeknd posts
('Blinding lights, but make it emotional 🌟', 10, NOW() - INTERVAL '4 hours', NULL),
('Can''t feel my face when the music hits just right', 10, NOW() - INTERVAL '1 day', NULL),
('After hours creativity is when magic happens ✨', 10, NOW() - INTERVAL '3 days', '/uploads/post_images/abel_post.jpg'),

-- Adele posts
('Hello, it''s me wondering if you''re ready for album 31 👋', 11, NOW() - INTERVAL '6 hours', NULL),
('Rolling in the deep... thoughts about life and love', 11, NOW() - INTERVAL '4 days', NULL),
('Someone like you is out there, just keep believing 💫', 11, NOW() - INTERVAL '1 week', '/uploads/post_images/adele_post.jpg'),

-- Bruno Mars posts
('Just the way you are is exactly how you should be ✨', 12, NOW() - INTERVAL '3 hours', NULL),
('Uptown funk vibes all day, every day 🕺', 12, NOW() - INTERVAL '2 days', '/uploads/post_images/bruno_post.png'),
('Count on me to bring the groove to every situation', 12, NOW() - INTERVAL '6 days', NULL),

-- Beyoncé posts
('Formation energy never fades 👑', 13, NOW() - INTERVAL '2 hours', NULL),
('Coachella was legendary, but wait till you see what''s next.', 13, NOW() - INTERVAL '2 days', NULL),
('Queen things only. 🐝', 13, NOW() - INTERVAL '3 days', NULL),
-- Drake posts
('Certified Lover Boy vibes 💘', 14, NOW() - INTERVAL '1 hour', NULL),
('Started from the bottom... still grinding.', 14, NOW() - INTERVAL '1 day', NULL),
('Toronto forever 🦉', 14, NOW() - INTERVAL '3 days', NULL),
-- Rihanna posts
('Makeup mogul by day, pop queen by night 💄🎤', 15, NOW() - INTERVAL '5 hours', NULL),
('New album? Maybe. Maybe not. 😏', 15, NOW() - INTERVAL '2 days', NULL),
('Umbrella ella ella ☔️', 15, NOW() - INTERVAL '4 days', NULL),
-- Lady Gaga posts
('Born this way, thriving this way 🌈', 16, NOW() - INTERVAL '1 hour', NULL),
('Little monsters forever 💖', 16, NOW() - INTERVAL '2 days', NULL),
('The Haus of Gaga is in session 🎭', 16, NOW() - INTERVAL '3 days', NULL),
-- Khalid posts
('Location 2.0 dropping soon 🌍', 17, NOW() - INTERVAL '3 hours', NULL),
('Grateful for the growth 🌱', 17, NOW() - INTERVAL '1 day', NULL),
('Soul music for the soul 🎶', 17, NOW() - INTERVAL '2 days', NULL),
-- Lizzo posts
('Feeling good as hell 💅', 18, NOW() - INTERVAL '30 minutes', NULL),
('Flute solos and body positivity 🌀', 18, NOW() - INTERVAL '1 day', NULL),
('Juice is forever 🧃', 18, NOW() - INTERVAL '3 days', NULL),
-- SZA posts
('Ctrl never left my rotation 🔁', 19, NOW() - INTERVAL '2 hours', NULL),
('Snooze is not just a song—it''s a mood.', 19, NOW() - INTERVAL '2 days', NULL),
('Real ones know the vibes 💫', 19, NOW() - INTERVAL '4 days', NULL),
-- Shawn Mendes posts
('Stitches healed, voice still strong 🎤', 20, NOW() - INTERVAL '1 hour', NULL),
('Wonder tour memories hitting hard 🌎', 20, NOW() - INTERVAL '1 day', NULL),
('Canadian maple beats 🍁', 20, NOW() - INTERVAL '3 days', NULL),
-- Camila Cabello posts
('Havana ooh na-na 💃', 21, NOW() - INTERVAL '3 hours', NULL),
('Familia over everything ❤️', 21, NOW() - INTERVAL '1 day', NULL),
('Finding new rhythm in life and music 🎶', 21, NOW() - INTERVAL '4 days', NULL),
-- Charlie Puth posts
('Attention to detail 🎧', 22, NOW() - INTERVAL '1 hour', NULL),
('Perfect pitch and awkward tweets 😅', 22, NOW() - INTERVAL '2 days', NULL),
('Light switch era is here 💡', 22, NOW() - INTERVAL '3 days', NULL),
-- BTS posts
('Bangtan power united 🌟', 23, NOW() - INTERVAL '2 hours', NULL),
('ARMY, you are everything 💜', 23, NOW() - INTERVAL '2 days', NULL),
('Mic drop! 🎤', 23, NOW() - INTERVAL '4 days', NULL),
-- Zayn posts
('Zquad assemble 🖤', 24, NOW() - INTERVAL '1 hour', NULL),
('Pillowtalk is a lifestyle', 24, NOW() - INTERVAL '2 days', NULL),
('Solo and thriving ✨', 24, NOW() - INTERVAL '3 days', NULL),
-- Halsey posts
('Colors of creativity 🔥', 25, NOW() - INTERVAL '1.5 hours', NULL),
('Without Me was never really about just me.', 25, NOW() - INTERVAL '2 days', NULL),
('Exploring new sonic palettes 🎨', 25, NOW() - INTERVAL '3 days', NULL),
-- Doja Cat posts
('Planet Her, population: me 🌍✨', 26, NOW() - INTERVAL '2 hours', NULL),
('Mooo! still my proudest track 🐄', 26, NOW() - INTERVAL '1 day', NULL),
('The internet wasn''t ready for me 🤖', 26, NOW() - INTERVAL '2 days', NULL),
-- Nicki Minaj posts
('Barbz stand up 💅', 27, NOW() - INTERVAL '1 hour', NULL),
('Queen Radio live again soon 🎙️', 27, NOW() - INTERVAL '2 days', NULL),
('Chun-Li energy 24/7 🥋', 27, NOW() - INTERVAL '3 days', NULL);

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
(10, 12, NOW() - INTERVAL '2 years'), (12, 10, NOW() - INTERVAL '2 years'), -- Weeknd & Bruno

-- Beyoncé
(13, 15, NOW() - INTERVAL '4 years'), (15, 13, NOW() - INTERVAL '4 years'),
(13, 1, NOW() - INTERVAL '5 years'), (1, 13, NOW() - INTERVAL '5 years'),
(13, 3, NOW() - INTERVAL '3 years'), (3, 13, NOW() - INTERVAL '3 years'),
(13, 27, NOW() - INTERVAL '6 years'), (27, 13, NOW() - INTERVAL '6 years'),
(13, 16, NOW() - INTERVAL '2 years'), (16, 13, NOW() - INTERVAL '2 years'),
(13, 14, NOW() - INTERVAL '1 year'), (14, 13, NOW() - INTERVAL '1 year'),
(13, 18, NOW() - INTERVAL '2.5 years'), (18, 13, NOW() - INTERVAL '2.5 years'),
(13, 2, NOW() - INTERVAL '1.5 years'), (2, 13, NOW() - INTERVAL '1.5 years'),
(13, 6, NOW() - INTERVAL '3 years'), (6, 13, NOW() - INTERVAL '3 years'),

-- Drake
(14, 27, NOW() - INTERVAL '8 years'), (27, 14, NOW() - INTERVAL '8 years'),
(14, 15, NOW() - INTERVAL '6 years'), (15, 14, NOW() - INTERVAL '6 years'),
(14, 3, NOW() - INTERVAL '4 years'), (3, 14, NOW() - INTERVAL '4 years'),
(14, 6, NOW() - INTERVAL '5 years'), (6, 14, NOW() - INTERVAL '5 years'),
(14, 23, NOW() - INTERVAL '1.5 years'), (23, 14, NOW() - INTERVAL '1.5 years'),
(14, 10, NOW() - INTERVAL '3 years'), (10, 14, NOW() - INTERVAL '3 years'),
(14, 22, NOW() - INTERVAL '1 year'), (22, 14, NOW() - INTERVAL '1 year'),

-- Rihanna
(15, 27, NOW() - INTERVAL '7 years'), (27, 15, NOW() - INTERVAL '7 years'),
(15, 3, NOW() - INTERVAL '5 years'), (3, 15, NOW() - INTERVAL '5 years'),
(15, 16, NOW() - INTERVAL '3 years'), (16, 15, NOW() - INTERVAL '3 years'),
(15, 6, NOW() - INTERVAL '2 years'), (6, 15, NOW() - INTERVAL '2 years'),
(15, 18, NOW() - INTERVAL '4 years'), (18, 15, NOW() - INTERVAL '4 years'),
(15, 7, NOW() - INTERVAL '1 year'), (7, 15, NOW() - INTERVAL '1 year'),
(15, 25, NOW() - INTERVAL '2 years'), (25, 15, NOW() - INTERVAL '2 years'),

-- Lady Gaga
(16, 18, NOW() - INTERVAL '1.5 years'), (18, 16, NOW() - INTERVAL '1.5 years'),
(16, 25, NOW() - INTERVAL '3 years'), (25, 16, NOW() - INTERVAL '3 years'),
(16, 4, NOW() - INTERVAL '2 years'), (4, 16, NOW() - INTERVAL '2 years'),
(16, 9, NOW() - INTERVAL '1 year'), (9, 16, NOW() - INTERVAL '1 year'),
(16, 26, NOW() - INTERVAL '1.5 years'), (26, 16, NOW() - INTERVAL '1.5 years'),
(16, 27, NOW() - INTERVAL '2 years'), (27, 16, NOW() - INTERVAL '2 years'),

-- Khalid
(17, 5, NOW() - INTERVAL '2 years'), (5, 17, NOW() - INTERVAL '2 years'),
(17, 18, NOW() - INTERVAL '2.5 years'), (18, 17, NOW() - INTERVAL '2.5 years'),
(17, 6, NOW() - INTERVAL '3 years'), (6, 17, NOW() - INTERVAL '3 years'),
(17, 7, NOW() - INTERVAL '2 years'), (7, 17, NOW() - INTERVAL '2 years'),
(17, 21, NOW() - INTERVAL '2 years'), (21, 17, NOW() - INTERVAL '2 years'),
(17, 19, NOW() - INTERVAL '1 year'), (19, 17, NOW() - INTERVAL '1 year'),
(17, 24, NOW() - INTERVAL '1.5 years'), (24, 17, NOW() - INTERVAL '1.5 years'),

-- Lizzo
(18, 27, NOW() - INTERVAL '2 years'), (27, 18, NOW() - INTERVAL '2 years'),
(18, 2, NOW() - INTERVAL '1.5 years'), (2, 18, NOW() - INTERVAL '1.5 years'),
(18, 25, NOW() - INTERVAL '2 years'), (25, 18, NOW() - INTERVAL '2 years'),
(18, 26, NOW() - INTERVAL '1.5 years'), (26, 18, NOW() - INTERVAL '1.5 years'),
(18, 19, NOW() - INTERVAL '1 year'), (19, 18, NOW() - INTERVAL '1 year'),

-- SZA
(19, 25, NOW() - INTERVAL '2 years'), (25, 19, NOW() - INTERVAL '2 years'),
(19, 6, NOW() - INTERVAL '2 years'), (6, 19, NOW() - INTERVAL '2 years'),
(19, 4, NOW() - INTERVAL '1 year'), (4, 19, NOW() - INTERVAL '1 year'),
(19, 7, NOW() - INTERVAL '1.5 years'), (7, 19, NOW() - INTERVAL '1.5 years'),
(19, 20, NOW() - INTERVAL '2 years'), (20, 19, NOW() - INTERVAL '2 years'),
(19, 21, NOW() - INTERVAL '1.5 years'), (21, 19, NOW() - INTERVAL '1.5 years'),

-- Shawn Mendes
(20, 21, NOW() - INTERVAL '3 years'), (21, 20, NOW() - INTERVAL '3 years'),
(20, 22, NOW() - INTERVAL '2 years'), (22, 20, NOW() - INTERVAL '2 years'),
(20, 3, NOW() - INTERVAL '2 years'), (3, 20, NOW() - INTERVAL '2 years'),
(20, 4, NOW() - INTERVAL '1.5 years'), (4, 20, NOW() - INTERVAL '1.5 years'),
(20, 24, NOW() - INTERVAL '2 years'), (24, 20, NOW() - INTERVAL '2 years'),
(20, 1, NOW() - INTERVAL '3 years'), (1, 20, NOW() - INTERVAL '3 years'),

-- Camila Cabello
(21, 7, NOW() - INTERVAL '2 years'), (7, 21, NOW() - INTERVAL '2 years'),
(21, 5, NOW() - INTERVAL '3 years'), (5, 21, NOW() - INTERVAL '3 years'),
(21, 6, NOW() - INTERVAL '3 years'), (6, 21, NOW() - INTERVAL '3 years'),
(21, 4, NOW() - INTERVAL '2 years'), (4, 21, NOW() - INTERVAL '2 years'),

-- Charlie Puth
(22, 6, NOW() - INTERVAL '2 years'), (6, 22, NOW() - INTERVAL '2 years'),
(22, 2, NOW() - INTERVAL '1 year'), (2, 22, NOW() - INTERVAL '1 year'),
(22, 1, NOW() - INTERVAL '2 years'), (1, 22, NOW() - INTERVAL '2 years'),
(22, 25, NOW() - INTERVAL '1.5 years'), (25, 22, NOW() - INTERVAL '1.5 years'),
(22, 3, NOW() - INTERVAL '2 years'), (3, 22, NOW() - INTERVAL '2 years'),

-- BTS
(23, 1, NOW() - INTERVAL '2 years'), (1, 23, NOW() - INTERVAL '2 years'),
(23, 2, NOW() - INTERVAL '1 year'), (2, 23, NOW() - INTERVAL '1 year'),
(23, 9, NOW() - INTERVAL '1.5 years'), (9, 23, NOW() - INTERVAL '1.5 years'),
(23, 8, NOW() - INTERVAL '2 years'), (8, 23, NOW() - INTERVAL '2 years'),
(23, 6, NOW() - INTERVAL '2 years'), (6, 23, NOW() - INTERVAL '2 years'),

-- Zayn
(24, 6, NOW() - INTERVAL '3 years'), (6, 24, NOW() - INTERVAL '3 years'),
(24, 27, NOW() - INTERVAL '2 years'), (27, 24, NOW() - INTERVAL '2 years'),
(24, 25, NOW() - INTERVAL '1 year'), (25, 24, NOW() - INTERVAL '1 year');





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
(28, 6, 'Ready for album 31, always! 👋', NOW() - INTERVAL '5 hours'),

-- Beyoncé posts (post_id 37-39)
(37, 14, 'Legend inspiring legends 🔥', NOW() - INTERVAL '1 hour'),
(37, 15, 'You were born to lead. 👑', NOW() - INTERVAL '30 minutes'),
(38, 1, 'Still thinking about that Coachella set!', NOW() - INTERVAL '2 hours'),

-- Drake posts (post_id 40-42)
(40, 27, 'Certified 🔥', NOW() - INTERVAL '1 hour'),
(42, 13, 'Toronto salute 🇨🇦', NOW() - INTERVAL '3 hours'),

-- Rihanna posts (post_id 43-45)
(43, 18, 'Queen of ALL trades 💄🎤', NOW() - INTERVAL '1.5 hours'),
(44, 14, 'We need that album tho...', NOW() - INTERVAL '2 hours'),
(45, 6, 'Ella ella ella ☔️ never gets old', NOW() - INTERVAL '5 hours'),

-- Gaga posts (post_id 46-48)
(46, 25, 'You’ve always been iconic 💅', NOW() - INTERVAL '3 hours'),
(48, 13, 'The Haus is in full force 🎭', NOW() - INTERVAL '4 hours'),

-- SZA posts (post_id 55-57)
(55, 19, 'This is why we ctrl everything 🔁', NOW() - INTERVAL '1 hour'),

-- Halsey posts (post_id 64-66)
(66, 19, 'Your palette never misses 🎨', NOW() - INTERVAL '1.5 hours'),

-- Doja Cat posts (post_id 67-69)
(68, 18, 'MOoo is eternal 🐄', NOW() - INTERVAL '3 hours'),

-- Nicki Minaj posts (post_id 70-72)
(70, 14, 'Barbz forever 💗', NOW() - INTERVAL '45 minutes');

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
(6, 28, NOW() - INTERVAL '4.5 hours'), (5, 28, NOW() - INTERVAL '3 hours'),

-- Beyoncé (37-39)
(14, 37, NOW() - INTERVAL '1 hour'), (15, 37, NOW() - INTERVAL '45 minutes'), (1, 37, NOW() - INTERVAL '30 minutes'), (27, 37, NOW() - INTERVAL '20 minutes'),
(2, 38, NOW() - INTERVAL '1 day'), (16, 39, NOW() - INTERVAL '2 hours'), (18, 38, NOW() - INTERVAL '3 hours'),

-- Drake (40-42)
(13, 40, NOW() - INTERVAL '1.5 hours'), (27, 40, NOW() - INTERVAL '1 hour'), (15, 41, NOW() - INTERVAL '3 hours'),
(6, 42, NOW() - INTERVAL '2 hours'), (9, 42, NOW() - INTERVAL '1 hour'), (22, 42, NOW() - INTERVAL '30 minutes'),

-- Rihanna (43-45)
(14, 43, NOW() - INTERVAL '2 hours'), (18, 44, NOW() - INTERVAL '1 hour'), (6, 45, NOW() - INTERVAL '5 hours'),
(1, 44, NOW() - INTERVAL '3 hours'), (25, 43, NOW() - INTERVAL '2 hours'), (26, 45, NOW() - INTERVAL '1 hour'),

-- Gaga (46-48)
(13, 46, NOW() - INTERVAL '3 hours'), (18, 46, NOW() - INTERVAL '2 hours'), (25, 47, NOW() - INTERVAL '3 hours'),
(19, 48, NOW() - INTERVAL '1 hour'), (5, 46, NOW() - INTERVAL '1 day'), (4, 47, NOW() - INTERVAL '2 days'),

-- Khalid (49-51)
(6, 49, NOW() - INTERVAL '30 minutes'), (18, 49, NOW() - INTERVAL '1 hour'), (21, 50, NOW() - INTERVAL '45 minutes'),
(7, 50, NOW() - INTERVAL '3 hours'), (20, 51, NOW() - INTERVAL '4 hours'),

-- Lizzo (52-54)
(16, 52, NOW() - INTERVAL '30 minutes'), (25, 52, NOW() - INTERVAL '1 hour'), (13, 53, NOW() - INTERVAL '2 hours'),
(14, 54, NOW() - INTERVAL '1 hour'), (6, 54, NOW() - INTERVAL '2 hours'),

-- SZA (55-57)
(18, 55, NOW() - INTERVAL '1.5 hours'), (25, 55, NOW() - INTERVAL '2 hours'), (19, 55, NOW() - INTERVAL '1 hour'),
(27, 56, NOW() - INTERVAL '2 hours'), (1, 57, NOW() - INTERVAL '3 hours'),

-- Shawn Mendes (58-60)
(21, 58, NOW() - INTERVAL '1 hour'), (22, 58, NOW() - INTERVAL '2 hours'), (20, 59, NOW() - INTERVAL '1 hour'),
(19, 60, NOW() - INTERVAL '2 hours'), (7, 58, NOW() - INTERVAL '3 hours'),

-- Camila Cabello (61-63)
(20, 61, NOW() - INTERVAL '1 hour'), (21, 62, NOW() - INTERVAL '1.5 hours'), (18, 63, NOW() - INTERVAL '3 hours'),
(5, 62, NOW() - INTERVAL '2 days'), (6, 63, NOW() - INTERVAL '1 day'),

-- Charlie Puth (64-66)
(14, 64, NOW() - INTERVAL '1 hour'), (1, 64, NOW() - INTERVAL '1 hour'), (25, 65, NOW() - INTERVAL '1 hour'),
(3, 66, NOW() - INTERVAL '2 hours'), (6, 66, NOW() - INTERVAL '1 hour'),

-- BTS (67-69)
(2, 67, NOW() - INTERVAL '2 hours'), (9, 68, NOW() - INTERVAL '3 hours'), (23, 68, NOW() - INTERVAL '2 hours'),
(14, 69, NOW() - INTERVAL '4 hours'), (4, 67, NOW() - INTERVAL '2 days'),

-- Zayn (70-72)
(15, 70, NOW() - INTERVAL '1 hour'), (24, 70, NOW() - INTERVAL '1.5 hours'), (26, 71, NOW() - INTERVAL '1 hour'),
(10, 72, NOW() - INTERVAL '2 hours'), (7, 71, NOW() - INTERVAL '3 hours'),

-- Halsey (73-75)
(19, 73, NOW() - INTERVAL '1 hour'), (25, 74, NOW() - INTERVAL '2 hours'), (14, 75, NOW() - INTERVAL '3 hours'),
(16, 73, NOW() - INTERVAL '4 hours'),

-- Doja Cat (76-78)
(18, 76, NOW() - INTERVAL '2 hours'), (14, 76, NOW() - INTERVAL '3 hours'), (27, 77, NOW() - INTERVAL '4 hours'),
(16, 78, NOW() - INTERVAL '5 hours'),

-- Nicki Minaj (79-81)
(14, 79, NOW() - INTERVAL '45 minutes'), (15, 79, NOW() - INTERVAL '1 hour'), (26, 80, NOW() - INTERVAL '1 hour'),
(13, 81, NOW() - INTERVAL '3 hours'), (27, 80, NOW() - INTERVAL '1 hour');

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
(7, 10, NOW() - INTERVAL '45 minutes'), (8, 10, NOW() - INTERVAL '30 minutes'),
(13, 1, NOW() - INTERVAL '30 minutes'), (15, 1, NOW() - INTERVAL '20 minutes'),
(27, 2, NOW() - INTERVAL '25 minutes'), (14, 3, NOW() - INTERVAL '1 hour'),
(13, 4, NOW() - INTERVAL '2 hours'), (27, 5, NOW() - INTERVAL '1 hour'),
(1, 5, NOW() - INTERVAL '30 minutes'),(14, 6, NOW() - INTERVAL '1 hour'), (18, 6, NOW() - INTERVAL '45 minutes'),
(6, 7, NOW() - INTERVAL '1 hour'), (25, 8, NOW() - INTERVAL '2 hours'),
(13, 9, NOW() - INTERVAL '3 hours'), (18, 9, NOW() - INTERVAL '2 hours'),
(16, 10, NOW() - INTERVAL '1.5 hours'),
(19, 11, NOW() - INTERVAL '1 hour'), (25, 11, NOW() - INTERVAL '45 minutes'),
(19, 12, NOW() - INTERVAL '1.5 hours'), (18, 12, NOW() - INTERVAL '1 hour'),
(14, 13, NOW() - INTERVAL '3 hours'), (27, 13, NOW() - INTERVAL '2 hours'),
(13, 14, NOW() - INTERVAL '1 hour'), (15, 14, NOW() - INTERVAL '45 minutes');

-- Insert Notifications
INSERT INTO notifications (receiving_user_id, sending_user_id, type, post_id, comment_id, is_read, created_at) VALUES
-- Post like notifications
(1, 2, 'postLike', 1, NULL, true, NOW() - INTERVAL '1.5 hours'),
(1, 9, 'postLike', 1, NULL, false, NOW() - INTERVAL '1 hour'),
(1, 8, 'postLike', 2, NULL, false, NOW() - INTERVAL '15 hours'),
(2, 1, 'postLike', 4, NULL, true, NOW() - INTERVAL '3.5 hours'),
(2, 12, 'postLike', 5, NULL, false, NOW() - INTERVAL '1.5 days'),

-- Comment notifications
(1, 2, 'comment', 1, 1, true, NOW() - INTERVAL '1 hour'),
(1, 9, 'comment', 1, 2, false, NOW() - INTERVAL '45 minutes'),
(1, 8, 'comment', 2, 3, false, NOW() - INTERVAL '12 hours'),
(2, 1, 'comment', 4, 4, true, NOW() - INTERVAL '3 hours'),
(3, 8, 'comment', 8, 8, false, NOW() - INTERVAL '20 hours'),

-- Comment like notifications
(2, 1, 'commentLike', 1, 1, true, NOW() - INTERVAL '50 minutes'),
(9, 1, 'commentLike', 1, 2, false, NOW() - INTERVAL '35 minutes'),
(8, 1, 'commentLike', 2, 3, false, NOW() - INTERVAL '10 hours');

-- Friend request notifications
--(11, 4, 'friend_request', NULL, NULL, false, NOW() - INTERVAL '2 days'),
--(1, 10, 'friend_request', NULL, NULL, false, NOW() - INTERVAL '1 day'),
--(3, 7, 'friend_request', NULL, NULL, true, NOW() - INTERVAL '1 week'),
--(5, 11, 'friend_request', NULL, NULL, true, NOW() - INTERVAL '2 weeks'),
--(10, 6, 'friend_request', NULL, NULL, false, NOW() - INTERVAL '3 days');

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