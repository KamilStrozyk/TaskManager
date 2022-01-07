CREATE SEQUENCE sq_role START WITH 1 INCREMENT BY 1 CACHE 1 NO CYCLE;

CREATE TABLE role
(
    id        BIGINT      not null,
    user_id   BIGINT      not null,
    role_name VARCHAR(50) not null
);

ALTER TABLE role
    ADD PRIMARY KEY (id);

ALTER TABLE role
    ADD CONSTRAINT fk_role_user FOREIGN KEY (user_id) REFERENCES "user";