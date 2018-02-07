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
  status          VARCHAR(100)   NOT NULL REFERENCES status_essence (status)
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
  date TIMESTAMP PRIMARY KEY
);

CREATE TABLE message (
  message_id UUID PRIMARY KEY,
  text       VARCHAR(1000),
  date       TIMESTAMP REFERENCES date_of_message (date),
  author_id  UUID REFERENCES user_essence (user_essence_id)
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
