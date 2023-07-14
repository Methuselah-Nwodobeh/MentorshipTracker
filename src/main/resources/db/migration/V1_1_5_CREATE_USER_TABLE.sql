CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id UUID REFERENCES roles(id),
    verified BOOLEAN NOT NULL,
    date_created TIMESTAMP,
    date_modified TIMESTAMP
);