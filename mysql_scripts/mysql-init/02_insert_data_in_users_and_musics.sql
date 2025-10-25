-- Insert Users (5 users)
INSERT INTO users (name, password, email, created_at) VALUES
('John Smith', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'john.smith@email.com', '2024-01-15 10:30:00'),
('Emma Johnson', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'emma.johnson@email.com', '2024-02-20 14:22:00'),
('Michael Chen', '$2a$10$xHzWkf45uzjy1RqNVwLtceS9dKxwZL7jQ5O9JzNVwPM1NMtJ8z8Gi', 'michael.chen@email.com', '2024-03-10 09:15:00'),
('Sarah Williams', '$2a$10$ZfKxP3M4NqNvLqNV9L8QuO9T5z6kF1rXsJ8hG3mQ2pY7nK4wE6jK.', 'sarah.williams@email.com', '2024-04-05 16:45:00'),
('David Brown', '$2a$10$H9qO8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'david.brown@email.com', '2024-05-12 11:20:00');

-- Insert Documents (10 documents with hash keys for S3)
INSERT INTO documents (name, size, file_extension, hash_key, created_at) VALUES
('Summer Vibes.mp3', 4523000, 'mp3', 'a3f8d9e2c1b4567890abcdef12345678', '2024-06-01 08:00:00'),
('Night Dreams.mp3', 3876000, 'mp3', 'b7c4e1f9a2d3456789bcdef0123456ab', '2024-06-03 10:30:00'),
('Electric Pulse.mp3', 5234000, 'mp3', 'c8d5f2e0b3a4567890cdef123456789c', '2024-06-05 14:15:00'),
('Acoustic Soul.mp3', 4102000, 'mp3', 'd9e6f3a1c4b567890def23456789abcd', '2024-06-08 09:45:00'),
('Urban Beat.mp3', 3654000, 'mp3', 'e0f7a4b2d5c678901ef3456789abcdef', '2024-06-10 16:20:00'),
('Ocean Waves.mp3', 4890000, 'mp3', 'f1a8b5c3e6d789012fa456789bcdef01', '2024-06-12 11:00:00'),
('Midnight Jazz.mp3', 5567000, 'mp3', 'a2b9c6d4f7e890123ab56789cdef0123', '2024-06-15 13:30:00'),
('Mountain Echo.mp3', 4321000, 'mp3', 'b3c0d7e5a8f901234bc6789def012345', '2024-06-18 15:45:00'),
('Sunrise Melody.mp3', 3987000, 'mp3', 'c4d1e8f6b9a012345cd789ef01234567', '2024-06-20 08:30:00'),
('Digital Dreams.mp3', 4456000, 'mp3', 'd5e2f9a7c0b123456de89f012345678a', '2024-06-22 12:15:00');

-- Insert Musics (10 music tracks linked to users and documents)
INSERT INTO musics (name, user_id, document_id, number_of_likes, created_at) VALUES
('Summer Vibes', 1, 1, 145, '2024-06-01 08:30:00'),
('Night Dreams', 2, 2, 89, '2024-06-03 11:00:00'),
('Electric Pulse', 3, 3, 203, '2024-06-05 14:45:00'),
('Acoustic Soul', 1, 4, 67, '2024-06-08 10:15:00'),
('Urban Beat', 4, 5, 178, '2024-06-10 16:50:00'),
('Ocean Waves', 2, 6, 234, '2024-06-12 11:30:00'),
('Midnight Jazz', 5, 7, 156, '2024-06-15 14:00:00'),
('Mountain Echo', 3, 8, 92, '2024-06-18 16:15:00'),
('Sunrise Melody', 4, 9, 121, '2024-06-20 09:00:00'),
('Digital Dreams', 5, 10, 187, '2024-06-22 12:45:00');

-- Insert User Likes (various users liking different music tracks)
INSERT INTO user_likes (music_id, user_id) VALUES
-- Music 1 likes
(1, 2), (1, 3), (1, 4), (1, 5),
-- Music 2 likes
(2, 1), (2, 3), (2, 5),
-- Music 3 likes
(3, 1), (3, 2), (3, 4), (3, 5),
-- Music 4 likes
(4, 2), (4, 3),
-- Music 5 likes
(5, 1), (5, 2), (5, 3), (5, 5),
-- Music 6 likes
(6, 1), (6, 3), (6, 4), (6, 5),
-- Music 7 likes
(7, 1), (7, 2), (7, 4),
-- Music 8 likes
(8, 1), (8, 2), (8, 5),
-- Music 9 likes
(9, 1), (9, 2), (9, 3),
-- Music 10 likes
(10, 1), (10, 2), (10, 3), (10, 4);