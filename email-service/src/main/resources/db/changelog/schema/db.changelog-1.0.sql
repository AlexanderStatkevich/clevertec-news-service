CREATE SCHEMA IF NOT EXISTS data;

CREATE TABLE IF NOT EXISTS data.verification_codes
(
    id    bigint PRIMARY KEY,
    email character varying(255),
    code  character varying(255)
);
