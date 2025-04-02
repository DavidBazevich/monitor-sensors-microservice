--liquibase formatted sql

--changeset david:1
INSERT INTO type(name) VALUES ('Pressure');
INSERT INTO type(name) VALUES ('Voltage');
INSERT INTO type(name) VALUES ('Temperature');
INSERT INTO type(name) VALUES ('Humidity');
--rollback DELETE FROM type;

--changeset david:2
INSERT INTO unit(name) VALUES ('bar');
INSERT INTO unit(name) VALUES ('voltage');
INSERT INTO unit(name) VALUES ('degree Celsius');
INSERT INTO unit(name) VALUES ('percent');
--rollback DELETE FROM unit;