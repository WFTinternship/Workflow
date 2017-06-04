INSERT INTO work_flow.apparea
    VALUE (10, 'Reporting', 'Some rep description', NULL );

INSERT INTO team
    VALUES (1, 'Team1', 'USA'),
        (2, 'Team2', 'Armenia'),
        (3, 'Team3', 'Bulgaria');

INSERT INTO user(id,first_name,last_name,email,passcode,rating)
    VALUES (11, 'John', 'Smith', 'john@gmail.com', '123465', 0),
       (22, 'Bob', 'Dilan', 'bob@gmail.com', '654977', 0),
       (33, 'Ken', 'Black', 'ken@gmail.com', '321654', 0);

INSERT INTO apparea(id,name,description,team_name)
    VALUES (111, 'AppArea1', 'AppArea1 Description', 1),
       (112, 'AppArea2', 'AppArea2 Description', 2),
       (113, 'AppArea3', 'AppArea3 Description', 3);

INSERT INTO user_apparea(user_id, apparea_id)
    VALUES (11, 111),
      (22, 112),
      (33,113),
      (11,113);

INSERT INTO post (id, post_id, user_id, date_time, title, content, apparea_id)
    VALUES (222, NULL, 11, '11/11/11 11:00:00', 'Post1', 'Post 1 content',111),
     (223, NULL, 22, '12/10/11 12:00:00', 'Post2', 'Post 2 content',112),
     (224, NULL, 33, '13/10/11 10:00:00', 'Post3', 'Post 3 content',113);

INSERT INTO comment(id,user_id, post_id, content,comment_time)
    VALUES ( 333,11, 222, 'Comment1', '11/11/11 11:00:00'),
     (334, 22, 224, 'Comment2', '10/10/10 11:00:00');

