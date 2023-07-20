-- Create advisors table
CREATE TABLE advisors (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    location varchar(255) NOT NULL ,
    role_id UUID REFERENCES roles(id),
    verified BOOLEAN NOT NULL DEFAULT FALSE,
    date_created TIMESTAMP,
    date_modified TIMESTAMP
);

-- Create advisees table
CREATE TABLE advisees
(
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    location varchar(255) NOT NULL ,
    role_id UUID REFERENCES roles(id),
    advisor_id UUID REFERENCES advisors(id),
    verified BOOLEAN NOT NULL DEFAULT FALSE,
    date_created TIMESTAMP,
    date_modified TIMESTAMP
);

-- Create role_permissions table for the many-to-many relationship
CREATE TABLE advisor_advisees (
    advisor_id UUID REFERENCES advisors(id),
    advisee_id UUID REFERENCES advisees(id),
     PRIMARY KEY (advisor_id, advisee_id)
);

