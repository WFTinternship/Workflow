
INSERT INTO user(first_name, last_name, email, passcode, rating)
VALUES ( 'John', 'Smith', 'john@gmail.com', '123465', 0),
  ( 'Bob', 'Dilan', 'bob@gmail.com', '654977', 0),
  ( 'Ken', 'Black', 'ken@gmail.com', '321654', 0);


INSERT INTO post (post_id, user_id, post_time, title, content, apparea_id)
VALUES ( NULL, 176, '11/11/11 11:00:00', 'Post1', 'Post 1 content',1),
  ( NULL, 176, '12/10/11 12:00:00', 'Post2', 'Post 2 content',1),
  ( NULL, 178, '13/10/11 10:00:00', 'Post3', 'Post 3 content',1);

INSERT INTO comment
VALUES (333, 11, 222, 'Comment1', '11/11/11 11:00:00'),
  (334, 22, 224, 'Comment2', '10/10/10 11:00:00');


SELECT * from post;

