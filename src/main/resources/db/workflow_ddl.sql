DROP TABLE IF EXISTS notification;
DROP TABLE IF EXISTS user_post_likes;
DROP TABLE IF EXISTS user_post_dislikes;
DROP TABLE IF EXISTS user_apparea;
DROP TABLE IF EXISTS best_answer;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS apparea;


CREATE TABLE IF NOT EXISTS user (
  id         BIGINT(25)  NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(45) NOT NULL,
  last_name  VARCHAR(45) NOT NULL,
  email      VARCHAR(45) NOT NULL UNIQUE,
  passcode   VARCHAR(256),
  avatar_url VARCHAR(100),
  rating     INT         NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS apparea (
  id          BIGINT(25)  NOT NULL,
  name        VARCHAR(45) NULL,
  description VARCHAR(45) NULL,
  team_name   VARCHAR(25) NULL,
  PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS
  user_apparea (
  user_id    BIGINT(25) NOT NULL,
  apparea_id BIGINT(25) NOT NULL,
  UNIQUE (user_id, apparea_id),
  INDEX fk_appareaId_idx (apparea_id ASC),
  CONSTRAINT fk_userId
  FOREIGN KEY (user_id)
  REFERENCES
    user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_appareaId
  FOREIGN KEY (apparea_id)
  REFERENCES
    apparea (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS
  post (
  id              BIGINT(25) AUTO_INCREMENT NOT NULL,
  post_id         BIGINT(25),
  user_id         BIGINT(25)                NOT NULL,
  post_time       TIMESTAMP                 NOT NULL DEFAULT CURRENT_TIMESTAMP,
  title           VARCHAR(200)               NOT NULL,
  content         VARCHAR(20000)             NOT NULL,
  apparea_id      BIGINT(25)                NOT NULL,
  INDEX fk_userId_idx (user_id ASC),
  INDEX fk_appareaId_idx (apparea_id ASC),
  PRIMARY KEY (id),
  CONSTRAINT fk_userId_post
  FOREIGN KEY (user_id)
  REFERENCES
    user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_appareaId_post
  FOREIGN KEY (apparea_id)
  REFERENCES
    apparea (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS
  comment (
  id           BIGINT(25)   NOT NULL AUTO_INCREMENT,
  user_id      BIGINT(25)   NULL,
  post_id      BIGINT(25)   NULL,
  content      VARCHAR(500) NOT NULL,
  comment_time TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX fk_userid_comment_idx (user_id ASC),
  INDEX fk_postid_comment_idx (post_id ASC),
  CONSTRAINT fk_userid_comment
  FOREIGN KEY (user_id)
  REFERENCES
    user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_postid_comment
  FOREIGN KEY (post_id)
  REFERENCES
    post (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS
  best_answer (
  post_id   BIGINT(25),
  answer_id BIGINT(25),
  PRIMARY KEY (post_id),
  CONSTRAINT fk_post_id
  FOREIGN KEY (post_id)
  REFERENCES
    post (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_answer_id
  FOREIGN KEY (answer_id)
  REFERENCES
    post (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT exists
  user_post_likes (
  user_id BIGINT(25) NOT NULL,
  post_id BIGINT(25) NOT NULL,
  UNIQUE (user_id, post_id),
  INDEX fk_postId_idx (post_id ASC),
  CONSTRAINT fk_likesUserId
  FOREIGN KEY (user_id)
  REFERENCES
    user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_likesPostId
  FOREIGN KEY (post_id)
  REFERENCES
    post (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT exists
  user_post_dislikes (
  user_id BIGINT(25) NOT NULL,
  post_id BIGINT(25) NOT NULL,
  UNIQUE (user_id, post_id),
  INDEX fk_postId_idx (post_id ASC),
  CONSTRAINT fk_dislikesUserId
  FOREIGN KEY (user_id)
  REFERENCES
    user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_dislikesPostId
  FOREIGN KEY (post_id)
  REFERENCES
    post (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS notification (
  post_id BIGINT(25) NOT NULL,
  user_id BIGINT(25) NOT NULL,
  PRIMARY KEY (post_id, user_id),
  FOREIGN KEY (post_id)
  REFERENCES
    post (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (user_id)
  REFERENCES
    user (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);