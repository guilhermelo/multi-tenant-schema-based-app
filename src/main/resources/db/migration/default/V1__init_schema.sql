CREATE TABLE user
(
    id                 BIGINT AUTO_INCREMENT,
    username           VARCHAR(255) UNIQUE,
    password           VARCHAR(255)
);

CREATE TABLE note
(
    id                 BIGINT AUTO_INCREMENT,
    text               TEXT
);