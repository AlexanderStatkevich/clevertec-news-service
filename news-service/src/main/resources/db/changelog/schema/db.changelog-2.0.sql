-- Insert 20 news entries
INSERT INTO data.news ("time", title, text, username)
SELECT NOW() - INTERVAL '1 day' * (21 - n), -- Generate different timestamps for each news entry
       CONCAT('Title ', n),                 -- Generate different titles for each news entry
       CONCAT('Text ', n),                  -- Generate different texts for each news entry
       CONCAT('User ', n)                   -- Generate different usernames for each news entry
FROM GENERATE_SERIES(1, 20) AS n;

-- Insert 10 comments for each news entry
INSERT INTO data.comments ("time", text, username, news_id)
SELECT NOW() - INTERVAL '1 hour' * (11 - c), -- Generate different timestamps for each comment
       CONCAT('Comment ', c),                -- Generate different texts for each comment
       CONCAT('User ', c),                   -- Generate different usernames for each comment
       n                                     -- Reference the news entry ID
FROM GENERATE_SERIES(1, 20) AS n
         CROSS JOIN GENERATE_SERIES(1, 10) AS c;
