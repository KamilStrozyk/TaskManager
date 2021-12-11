CREATE SEQUENCE sq_user START WITH 1 INCREMENT BY 1 CACHE 1 NO CYCLE;

CREATE TABLE "user"
(
    id       BIGINT       not null,
    username VARCHAR(20)  not null,
    email    VARCHAR(50)  not null,
    password VARCHAR(120) not null
);

ALTER TABLE "user"
    ADD PRIMARY KEY (id);