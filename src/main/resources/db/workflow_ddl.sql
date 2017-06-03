DROP TABLE IF EXISTS work_flow.user_apparea;
DROP TABLE IF EXISTS work_flow.best_answer;
DROP TABLE IF EXISTS work_flow.comment;
DROP TABLE IF EXISTS work_flow.post;
DROP TABLE IF EXISTS work_flow.user;
DROP TABLE IF EXISTS work_flow.apparea;



CREATE TABLE IF NOT exists work_flow.user(
  id BIGINT(25) NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  email VARCHAR(45) NOT NULL ,
  passcode VARCHAR(45),
  rating INT NOT NULL,
  PRIMARY KEY (id));

CREATE TABLE IF NOT exists work_flow.apparea (
  id BIGINT(25) NOT NULL,
  name VARCHAR(45) NULL,
  description VARCHAR(45) NULL,
  team_name VARCHAR (25) NULL,
  PRIMARY KEY (id)
);


CREATE TABLE IF NOT exists work_flow.user_apparea (
  user_id BIGINT(25) NOT NULL,
  apparea_id BIGINT(25) NOT NULL,
  UNIQUE (user_id, apparea_id),
  INDEX fk_appareaId_idx (apparea_id ASC),
  CONSTRAINT fk_userId
  FOREIGN KEY (user_id)
  REFERENCES work_flow.user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_appareaId
  FOREIGN KEY (apparea_id)
  REFERENCES work_flow.apparea (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT exists work_flow.post (
  id BIGINT(25) AUTO_INCREMENT NOT NULL,
  post_id BIGINT(25),
  user_id BIGINT(25) NOT NULL,
  post_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  title VARCHAR(45) NOT NULL,
  content VARCHAR(1000) NOT NULL,
  apparea_id BIGINT(25) NOT NULL,
  INDEX fk_userId_idx (user_id ASC),
  INDEX fk_appareaId_idx (apparea_id ASC),
  PRIMARY KEY (id),
  CONSTRAINT fk_userId_post
  FOREIGN KEY (user_id)
  REFERENCES work_flow.user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_appareaId_post
  FOREIGN KEY (apparea_id)
  REFERENCES work_flow.apparea (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT exists work_flow.comment (
  id BIGINT(25) NOT NULL AUTO_INCREMENT,
  user_id BIGINT(25) NULL,
  post_id BIGINT(25) NULL,
  content VARCHAR(500) NULL,
  comment_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX fk_userid_comment_idx (user_id ASC),
  INDEX fk_postid_comment_idx (post_id ASC),
  CONSTRAINT fk_userid_comment
  FOREIGN KEY (user_id)
  REFERENCES work_flow.user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_postid_comment
  FOREIGN KEY (post_id)
  REFERENCES work_flow.post (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS work_flow.best_answer(
  post_id BIGINT(25),
  answer_id BIGINT(25),
  PRIMARY KEY (post_id),
  CONSTRAINT fk_post_id
  FOREIGN KEY (post_id)
  REFERENCES work_flow.post (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_answer_id
  FOREIGN KEY (answer_id)
  REFERENCES work_flow.post (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

