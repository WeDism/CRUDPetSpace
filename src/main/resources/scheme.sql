-- CREATE DATABASE pets_space;

DROP TABLE IF EXISTS status_essence CASCADE;
DROP TABLE IF EXISTS role_essence CASCADE;
DROP TABLE IF EXISTS user_essence CASCADE;
DROP TABLE IF EXISTS state_friend CASCADE;
DROP TABLE IF EXISTS friends CASCADE;
DROP TABLE IF EXISTS species_pet CASCADE;
DROP TABLE IF EXISTS pet CASCADE;
DROP TABLE IF EXISTS follow_pets CASCADE;
DROP TABLE IF EXISTS date_of_message CASCADE;
DROP TABLE IF EXISTS message CASCADE;
DROP TABLE IF EXISTS message_of_user CASCADE;

CREATE TABLE status_essence (
  status VARCHAR(100) PRIMARY KEY
);

CREATE TABLE role_essence (
  role VARCHAR(100) PRIMARY KEY
);

CREATE TABLE user_essence (
  user_essence_id UUID PRIMARY KEY,
  nickname        VARCHAR(300)   NOT NULL UNIQUE,
  name            VARCHAR(200)   NOT NULL,
  surname         VARCHAR(200)   NOT NULL,
  patronymic      VARCHAR(200),
  password        CHARACTER(32)  NOT NULL,
  email           CHARACTER(254) NOT NULL,
  phone           CHARACTER(15),
  birthday        TIMESTAMP,
  role            VARCHAR(100)   NOT NULL REFERENCES role_essence (role),
  status          VARCHAR(100)   NOT NULL REFERENCES status_essence (status),
  CONSTRAINT numbers CHECK (phone ~* '^[0-9]')
);

CREATE TABLE state_friend (
  state VARCHAR(100) PRIMARY KEY
);

CREATE TABLE friends (
  user_essence_id UUID REFERENCES user_essence (user_essence_id),
  friend_id       UUID REFERENCES user_essence (user_essence_id),
  status          VARCHAR(100) NOT NULL REFERENCES state_friend (state),
  CONSTRAINT pk_friends_id PRIMARY KEY (user_essence_id, friend_id)
);

CREATE TABLE species_pet (
  name VARCHAR(200) PRIMARY KEY
);

CREATE TABLE pet (
  pet_id          UUID PRIMARY KEY,
  name            VARCHAR(200),
  weight          REAL,
  birthday        TIMESTAMP,
  user_essence_id UUID         NOT NULL REFERENCES user_essence (user_essence_id),
  species         VARCHAR(200) NOT NULL REFERENCES species_pet (name)
);

CREATE TABLE follow_pets (
  pet_id          UUID REFERENCES pet (pet_id),
  user_essence_id UUID REFERENCES user_essence (user_essence_id),
  CONSTRAINT pk_follow_pets_id PRIMARY KEY (pet_id, user_essence_id)
);

CREATE TABLE date_of_message (
  date TIMESTAMP PRIMARY KEY  DEFAULT now()
);

CREATE TABLE message (
  message_id UUID PRIMARY KEY,
  text       TEXT,
  date       TIMESTAMP REFERENCES date_of_message (date),
  author_id  UUID NULL REFERENCES user_essence (user_essence_id)
);


CREATE TABLE message_of_user (
  message_id UUID REFERENCES message (message_id),
  owner_id   UUID REFERENCES user_essence (user_essence_id),
  date       TIMESTAMP REFERENCES date_of_message (date),
  CONSTRAINT pk_message_of_user_id PRIMARY KEY (message_id, owner_id, date)
);

INSERT INTO role_essence VALUES ('ROOT'), ('ADMIN'), ('USER');
INSERT INTO status_essence VALUES ('ACTIVE'), ('INACTIVE'), ('DELETED');
INSERT INTO state_friend VALUES ('REQUESTED'), ('REJECTED'), ('APPROVED');
INSERT INTO user_essence (user_essence_id, nickname, password, name, surname, email, role, status)
VALUES
  (uuid('8ae453ef-4a97-46e9-803d-8502a446e6dc'), 'root', 'root', 'Andrey', 'Krasnov', 'root@root', 'ROOT', 'ACTIVE');
INSERT INTO user_essence (user_essence_id, nickname, password, name, surname, email, role, status)
VALUES
  (uuid('14c88e00-a325-4ac7-8c04-a43bc72cdc4a'), 'user', 'user', 'Ivan', 'Makarenko', 'user@user', 'USER', 'ACTIVE');
INSERT INTO user_essence (user_essence_id, nickname, password, name, surname, email, role, status)
VALUES
  (uuid('7c20a4d7-5f9b-416f-a910-b13a816ba90b'), 'admin', 'admin', 'Petr', 'Shevtsov', 'USER@USER', 'ADMIN', 'ACTIVE');
--TODO create trigger for root on update and delete

SELECT * FROM friends;
SELECT f.user_essence_id AS essence_id, f.status FROM user_essence ue JOIN friends f USING(user_essence_id) WHERE f.friend_id='14c88e00-a325-4ac7-8c04-a43bc72cdc4a'::uuid;
SELECT f.friend_id AS essence_id, f.status FROM user_essence ue JOIN friends f ON ue.user_essence_id=f.friend_id AND f.user_essence_id='14c88e00-a325-4ac7-8c04-a43bc72cdc4a'::uuid;
UPDATE friends SET status='REQUESTED' WHERE user_essence_id='14c88e00-a325-4ac7-8c04-a43bc72cdc4a'::uuid AND friend_id='7c20a4d7-5f9b-416f-a910-b13a816ba90b'::uuid