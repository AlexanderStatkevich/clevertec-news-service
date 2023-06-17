CREATE SCHEMA IF NOT EXISTS data;

CREATE TABLE IF NOT EXISTS data.comments
(
    id       bigserial PRIMARY KEY,
    news_id  bigint REFERENCES data.news (id),
    "time"   timestamp(6) WITHOUT TIME ZONE,
    text     character varying(255),
    username character varying(255)
);

CREATE TABLE IF NOT EXISTS data.news_comments
(
    comments_id bigint REFERENCES data.comments (id),
    news_id     bigint REFERENCES data.news (id)
);
