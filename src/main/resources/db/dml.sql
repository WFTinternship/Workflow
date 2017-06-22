/*
INSERT INTO user(first_name, last_name, email, passcode, rating)
VALUES ( 'John', 'Smith', 'john@gmail.com', '123465', 0),
  ( 'Bob', 'Dilan', 'bob@gmail.com', '654977', 0),
  ( 'Ken', 'Black', 'ken@gmail.com', '321654', 0);*/

/*INSERT INTO apparea VALUES (1, "Reporting", "Some rep description", "Team1"),
(2, "API", "API", "Team1"),
(3, "Access Levels Security", "Access Levels Security", "Team1"),
(4, "Agile", "Agile", "Team1"),
(5, "Announcement Center", "Announcement Center", "Team1"),
(6, "Application Server", "Application Server", "Team1");*/

INSERT INTO post (post_id, user_id, post_time, title, content, apparea_id)
VALUES ( NULL, 3, '11/11/11 11:00:00', 'Post12', 'Post 12 postContent',6);
/*
INSERT INTO comment(user_id, post_id, content,comment_time)
VALUES ( 1, 4, 'Comment1', '11/11/11 11:00:00'),
  ( 2, 5, 'Comment2', '10/10/10 11:00:00');


SELECT * from post;

*/
