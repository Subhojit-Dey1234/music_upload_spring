-- Insert dummy users
INSERT INTO users (name, password, email, created_at) VALUES
('Alice Johnson', 'hashed_password_1', 'alice@example.com', '2024-01-15 10:30:00'),
('Bob Smith', 'hashed_password_2', 'bob@example.com', '2024-02-20 14:45:00'),
('Charlie Brown', 'hashed_password_3', 'charlie@example.com', '2024-03-10 09:15:00'),
('Diana Prince', 'hashed_password_4', 'diana@example.com', '2024-04-05 16:20:00');

-- Insert dummy music records
INSERT INTO musics (name, user_id, number_of_likes, created_at) VALUES
('Summer Vibes', 1, 245, '2024-05-01 11:00:00'),
('Midnight Echo', 1, 128, '2024-05-10 13:30:00'),
('Electric Dreams', 2, 512, '2024-05-15 10:45:00'),
('Jazz Nights', 2, 87, '2024-05-20 15:20:00'),
('Ocean Waves', 3, 356, '2024-05-25 12:10:00'),
('Sunset Boulevard', 3, 203, '2024-06-02 14:50:00'),
('Urban Pulse', 4, 421, '2024-06-08 09:30:00'),
('Starlight Serenade', 4, 189, '2024-06-15 17:45:00'),
('Golden Hour', 1, 267, '2024-06-20 11:20:00'),
('Neon Lights', 2, 334, '2024-06-25 13:15:00');