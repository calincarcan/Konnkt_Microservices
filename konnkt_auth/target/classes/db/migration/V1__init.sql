CREATE SCHEMA konnktdb;
SET search_path = konnktdb, pg_catalog;

-- Create Roles Table
CREATE TABLE Roles (
                       role_id SERIAL PRIMARY KEY,
                       role_name VARCHAR(50) NOT NULL UNIQUE
);

-- Create Users Table with Role Relationship
CREATE TABLE Users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       role_id INT REFERENCES Roles(role_id),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Forums Table
CREATE TABLE Forums (
                        forum_id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        description TEXT,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Posts Table
CREATE TABLE Posts (
                       post_id SERIAL PRIMARY KEY,
                       forum_id INT REFERENCES Forums(forum_id),
                       user_id INT REFERENCES Users(user_id),
                       title VARCHAR(255) NOT NULL,
                       content TEXT NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       score INT DEFAULT 0
);

-- Create Comments Table
CREATE TABLE Comments (
                          comment_id SERIAL PRIMARY KEY,
                          post_id INT REFERENCES Posts(post_id),
                          user_id INT REFERENCES Users(user_id),
                          content TEXT NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          score INT DEFAULT 0
);

-- Create Votes Table
CREATE TABLE Votes (
                       vote_id SERIAL PRIMARY KEY,
                       user_id INT REFERENCES Users(user_id),
                       post_id INT REFERENCES Posts(post_id),
                       comment_id INT REFERENCES Comments(comment_id),
                       vote_type SMALLINT CHECK (vote_type IN (-1, 1)), -- -1 for dislike, 1 for like
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       UNIQUE (user_id, post_id, comment_id)
);

-- Insert Initial Roles
INSERT INTO Roles (role_name) VALUES ('admin');
INSERT INTO Roles (role_name) VALUES ('user');
