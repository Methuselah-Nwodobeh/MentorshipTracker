-- Alter Roles and Permissions tables
ALTER TABLE admins
    ADD firstname VARCHAR(255) NOT NULL DEFAULT 'firstname1',
    ADD verified BOOLEAN NOT NULL DEFAULT FALSE;
