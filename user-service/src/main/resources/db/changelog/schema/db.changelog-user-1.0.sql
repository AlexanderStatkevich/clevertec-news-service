CREATE SCHEMA IF NOT EXISTS data;

CREATE TABLE IF NOT EXISTS data.users
(
    id               uuid PRIMARY KEY,
    date_time_create timestamp(6)        NOT NULL,
    date_time_update timestamp(6)        NOT NULL,
    email            varchar(255) UNIQUE NOT NULL,
    full_name        varchar(255)        NOT NULL,
    role             varchar(25)         NOT NULL,
    status           varchar(25)         NOT NULL,
    password         varchar(255)        NOT NULL
);

CREATE INDEX IF NOT EXISTS users_email_index ON data.users (email);

