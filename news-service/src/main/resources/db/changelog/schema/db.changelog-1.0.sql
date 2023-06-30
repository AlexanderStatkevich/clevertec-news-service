CREATE SCHEMA IF NOT EXISTS data;

CREATE TABLE IF NOT EXISTS data.news
(
    id     bigserial PRIMARY KEY,
    "time" timestamp(6) WITHOUT TIME ZONE,
    text   character varying(255),
    title  character varying(255)
);


CREATE TABLE IF NOT EXISTS data.comments
(
    id       bigserial PRIMARY KEY,
    "time"   timestamp(6) WITHOUT TIME ZONE,
    text     character varying(255),
    username character varying(255),
    news_id  bigint REFERENCES data.news (id)
);
