-- Alter Roles and Permissions tables
ALTER TABLE roles
ADD date_created TIMESTAMP,
ADD date_modified TIMESTAMP;

ALTER TABLE permissions
ADD date_created TIMESTAMP,
ADD date_modified TIMESTAMP;

ALTER TABLE role_permissions
ADD date_created TIMESTAMP,
ADD date_modified TIMESTAMP;
