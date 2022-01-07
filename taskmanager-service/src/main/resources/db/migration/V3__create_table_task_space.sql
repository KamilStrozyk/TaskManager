CREATE SEQUENCE sq_task_space START WITH 1 INCREMENT BY 1 CACHE 1 NO CYCLE;

CREATE TABLE task_space
(
    id         BIGINT      not null,
    user_id    BIGINT      not null,
    title      VARCHAR(50) not null,
    created_at DATE        not null
);

ALTER TABLE task_space
    ADD PRIMARY KEY (id);

ALTER TABLE task_space
    ADD CONSTRAINT fk_task_space_user FOREIGN KEY (user_id) REFERENCES "user";