-- CREATE DATABASE pets_space;

DROP TABLE IF EXISTS status_entry CASCADE;
DROP TABLE IF EXISTS role_entry CASCADE;
DROP TABLE IF EXISTS user_entry CASCADE;
DROP TABLE IF EXISTS state_friend CASCADE;
DROP TABLE IF EXISTS friends CASCADE;
DROP TABLE IF EXISTS species_pet CASCADE;
DROP TABLE IF EXISTS pet CASCADE;
DROP TABLE IF EXISTS follow_pets CASCADE;
DROP TABLE IF EXISTS date_of_message CASCADE;
DROP TABLE IF EXISTS message CASCADE;
DROP TABLE IF EXISTS message_of_user CASCADE;

CREATE TABLE status_entry (
  status VARCHAR(100) PRIMARY KEY
);

CREATE TABLE role_entry (
  role VARCHAR(100) PRIMARY KEY
);

CREATE TABLE user_entry (
  user_entry_id UUID PRIMARY KEY,
  nickname      VARCHAR(300)   NOT NULL UNIQUE,
  name          VARCHAR(200),
  surname       VARCHAR(200),
  pathronymic   VARCHAR(200),
  password      CHARACTER(32)  NOT NULL,
  email         CHARACTER(254) NOT NULL,
  birthday      TIMESTAMP,
  role          VARCHAR(100)   NOT NULL REFERENCES role_entry (role),
  status        VARCHAR(100)   NOT NULL REFERENCES status_entry (status)
);

CREATE TABLE state_friend (
  state VARCHAR(100) PRIMARY KEY
);

CREATE TABLE friends (
  entry_id  UUID         NOT NULL REFERENCES user_entry (user_entry_id),
  friend_id UUID         NOT NULL REFERENCES user_entry (user_entry_id),
  status    VARCHAR(100) NOT NULL REFERENCES state_friend (state),
  CONSTRAINT pk_friends_id PRIMARY KEY (entry_id, friend_id)
);

CREATE TABLE species_pet (
  name VARCHAR(200) PRIMARY KEY
);

CREATE TABLE pet (
  pet_id        UUID PRIMARY KEY,
  name          VARCHAR(200),
  weight        REAL,
  birthday      TIMESTAMP,
  user_entry_id UUID         NOT NULL REFERENCES user_entry (user_entry_id),
  species       VARCHAR(200) NOT NULL REFERENCES species_pet (name)
);

CREATE TABLE follow_pets (
  pet_id        UUID REFERENCES pet (pet_id),
  user_entry_id UUID REFERENCES user_entry (user_entry_id),
  CONSTRAINT pk_follow_pets_id PRIMARY KEY (pet_id, user_entry_id)
);

CREATE TABLE date_of_message (
  date TIMESTAMP PRIMARY KEY  DEFAULT now()
);

CREATE TABLE message (
  message_id UUID PRIMARY KEY,
  text       TEXT,
  date       TIMESTAMP REFERENCES date_of_message (date),
  author_id  UUID NOT NULL REFERENCES user_entry (user_entry_id)
);


CREATE TABLE message_of_user (
  message_id UUID REFERENCES message (message_id),
  owner_id   UUID REFERENCES user_entry (user_entry_id),
  date       TIMESTAMP REFERENCES date_of_message (date),
  CONSTRAINT pk_message_of_user_id PRIMARY KEY (message_id, owner_id, date)
);

INSERT INTO role_entry VALUES ('ROOT'), ('ADMIN'), ('USER');
INSERT INTO status_entry VALUES ('ACTIVE'), ('INACTIVE'), ('DELETED');
INSERT INTO user_entry (user_entry_id, nickname, password, email, role, status)
VALUES (uuid('8ae453ef-4a97-46e9-803d-8502a446e6dc'), 'root', 'root', 'root@root', 'ROOT', 'ACTIVE');
INSERT INTO user_entry (user_entry_id, nickname, password, email, role, status)
VALUES (uuid('14c88e00-a325-4ac7-8c04-a43bc72cdc4a'), 'user', 'user', 'user@user', 'USER', 'ACTIVE');
INSERT INTO user_entry (user_entry_id, nickname, password, email, role, status)
VALUES (uuid('7c20a4d7-5f9b-416f-a910-b13a816ba90b'), 'admin', 'admin', 'user@user', 'ADMIN', 'ACTIVE');
--TODO create trigger for root on update and delete