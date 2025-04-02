--liquibase formatted sql

--changeset david:1
CREATE TABLE IF NOT EXISTS type(
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);
--rollback DROP TABLE type;

--changeset david:2
CREATE TABLE IF NOT EXISTS unit(
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL UNIQUE
);
--rollback DROP TABLE unit;

--changeset david:3
CREATE TABLE IF NOT EXISTS sensor(
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL UNIQUE,
    model VARCHAR(15) NOT NULL,
    range_from INT NOT NULL,
    range_to INT NOT NULL,
    type_id INT  REFERENCES type(id) NOT NULL,
    unit_id INT REFERENCES unit(id),
    location VARCHAR(40),
    description VARCHAR(200)
);
--rollback DROP TABLE sensor;

--changeset david:4
CREATE TABLE IF NOT EXISTS users
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    role VARCHAR(20) NOT NULL,
    create_date TIMESTAMP NOT NULL ,
    last_modified_date TIMESTAMP
);
--rollback DROP TABLE users;

--changeset david:5
CREATE TABLE IF NOT EXISTS token
(
    id SERIAL PRIMARY KEY,
    token VARCHAR(256),
    expired BOOLEAN NOT NULL,
    revoked BOOLEAN NOT NULL,
    user_id INT NOT NULL REFERENCES users(id)
);
--rollback DROP TABLE token;