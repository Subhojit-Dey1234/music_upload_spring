USE music_details;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE musics (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    number_of_likes INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE documents (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    size INT NOT NULL,
    file_extension VARCHAR(50)
);

ALTER TABLE musics add column ( document_id INT NOT NULL );
ALTER TABLE documents add column ( hash_key VARCHAR(5000) NOT NULL );

CREATE TABLE user_likes(
    id INT PRIMARY KEY AUTO_INCREMENT,
    music_id INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (music_id) REFERENCES musics(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);