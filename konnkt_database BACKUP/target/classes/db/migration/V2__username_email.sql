-- Unique constraint on username and email

SET search_path = konnktdb, pg_catalog;

ALTER TABLE Users
    ADD CONSTRAINT unique_username_email UNIQUE (username, email);
