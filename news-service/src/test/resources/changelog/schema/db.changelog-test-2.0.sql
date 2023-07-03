
INSERT INTO data.news ("time", text, title, username)
VALUES ('2021-10-01 10:00:00', 'First test text', 'First News', 'journalist1'),
       ('2021-10-02 11:00:00', 'Second test text', 'Second News', 'journalist1'),
       ('2021-10-02 12:00:00', 'Third test text', 'Third News', 'journalist1'),
       ('2021-10-02 13:00:00', 'Fourth test text', 'Fourth News', 'journalist1'),
       ('2021-10-02 14:00:00', 'Fifth test text', 'Fifth News', 'journalist1');


INSERT INTO data.comments (news_id, "time", text, username)
VALUES (1, '2021-10-01 11:00:00', 'First text to first news', 'subscriber1'),
       (1, '2021-10-01 12:00:00', 'Second text to first news', 'subscriber2'),
       (1, '2021-10-01 13:00:00', 'Third text to first news', 'subscriber3'),
       (2, '2021-10-02 12:30:00', 'First text to second news', 'subscriber4'),
       (2, '2021-10-02 13:30:00', 'Second text to second news', 'subscriber5'),
       (2, '2021-10-02 14:30:00', 'Third text to second news', 'subscriber6'),
       (3, '2021-10-03 15:45:00', 'First text to third news', 'subscriber7'),
       (3, '2021-10-03 16:45:00', 'Second text to third news', 'subscriber8'),
       (3, '2021-10-03 17:45:00', 'Third text to third news', 'subscriber9'),
       (4, '2021-10-04 17:20:00', 'First text to fourth news', 'subscriber10'),
       (4, '2021-10-04 18:20:00', 'Second text to fourth news', 'subscriber11'),
       (4, '2021-10-04 19:20:00', 'Third text to fourth news', 'subscriber12'),
       (5, '2021-10-05 10:15:00', 'First text to fifth news', 'subscriber13'),
       (5, '2021-10-05 11:15:00', 'Second text to fifth news', 'subscriber14'),
       (5, '2021-10-05 12:15:00', 'Third text to fifth news', 'subscriber15');

