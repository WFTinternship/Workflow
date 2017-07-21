INSERT INTO work_flow.user (first_name, last_name, email, passcode, avatar_url, rating) VALUES ('John', 'Smith', 'john@gmail.com', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', '/images/default/user-avatar.png', 0);
INSERT INTO work_flow.user (first_name, last_name, email, passcode, avatar_url, rating) VALUES ('James', 'Gosling', 'jg@gmail.com', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', '/images/default/user-avatar.png', 0);
INSERT INTO work_flow.user (first_name, last_name, email, passcode, avatar_url, rating) VALUES ('Bill', 'Gates', 'bg@gmail.com', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', '/images/default/user-avatar.png', 0);
INSERT INTO work_flow.user (first_name, last_name, email, passcode, avatar_url, rating) VALUES ('Steve', 'Jobes', 'sj@gmail.com', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', '/images/default/user-avatar.png', 0);
INSERT INTO work_flow.user (first_name, last_name, email, passcode, avatar_url, rating) VALUES ('Steve', 'Wozniak', 'sw@gmail.com', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', '/images/default/user-avatar.png', 0);

INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (null, 2, '2017-07-21 17:30:58', 'Having multiple sessionFactory instances', 'I am porting a legacy application to hibernate 5 and Im having trouble with the login phase. Heres how it works (I cant change that):
user initially connects to oracle DB with a generic login/password (same for all users)
then user runs a "login" stored procedure and enters a unique password as parameter
the procedure returns a specific Oracle DB username/password to the user
user disconnects from DB and reconnects using the credentials given by the stored procedure
I currently create one instance of sessionFactory per connected user, but Im worried that this will impact performance. Is there a better way to do this?

Thanks.', 86);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (1, 3, '2017-07-21 23:32:26', 'Having multiple sessionFactory instances', 'Hibernate Multitenancy with "Separate database" strategy would work even if you are actually
    connecting to the same database but different credentials.
    MultiTenantConnectionProvider must be specified to return connection with right username and password.', 86);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (null, 4, '2017-07-21 17:34:19', 'Do you use UML in Agile development practices?

', 'It feels like an artifacts of an earlier days, but UML sure does have its use. However, agile processes like Extreme Programming advocates "embracing changes", does that also means I should make less documents and UML models as well? Since they gives the impression of setting something in stone.

Where does UML belongs in an Agile development practice? Other than the preliminary spec documents, should I use it at all?', 4);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (3, 3, '2017-07-21 18:42:31', 'Do you use UML in Agile development practices?

', 'Breeze through Robert Martin''s Agile Principles, Patterns and Practices

The suggestion is to use UML to communicate designs within the team.. a shared language ; anyone taking a look at the diagram can understand the solution (faster than talking about it) and contribute quicker.
If you find the team making the same diagrams over and over again, someone will make a good version and store it on the wiki / source control. Overtime the more useful diagrams will start to collate in that place.
Don''t spend too much time on it... you don''t need too much detail. Models are built in the architectural / construction realms because building a house to validate-test the design is expensive/infeasible. Software is not like that - you could validate your design by just coding it up in the time you make a UML model of your untested design (with all the bells and whistles).
So says UncleBob... I concur.', 4);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (3, 2, '2017-07-21 23:37:54', 'Do you use UML in Agile development practices?

', 'Use what you find helpful. If a UML diagram helps you visualize what you need to do, use it. If a whiteboard does the same thing, use that.

Just don''t make UML diagrams for the sake of making UML diagrams. Our time is too expensive to spend doing useless things.', 4);