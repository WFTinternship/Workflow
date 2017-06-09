INSERT INTO user
VALUES (11, 'John', 'Smith', 'john@gmail.com', '123465', 0),
  (22, 'Bob', 'Dilan', 'bob@gmail.com', '654977', 0),
  (33, 'Ken', 'Black', 'ken@gmail.com', '321654', 0);


INSERT INTO post (id, post_id, user_id, post_time, title, content, apparea_id)
VALUES (222, NULL, 11, '11/11/11 11:00:00', 'Post1', 'Post 1 content',1),
  (223, NULL, 22, '12/10/11 12:00:00', 'Post2', 'Post 2 content',1),
  (224, NULL, 33, '13/10/11 10:00:00', 'Post3', 'Post 3 content',1),
  (225, 222, 11, '11/11/11 11:00:00', 'Answer1', 'Answer 1 content',1),
  (226, 222, 22, '12/10/11 12:00:00', 'Answer1', 'Answer 2 content',1);

INSERT INTO comment
VALUES (333, 11, 222, 'Comment1', '11/11/11 11:00:00'),
  (334, 22, 224, 'Comment2', '10/10/10 11:00:00');


SELECT * from post;

