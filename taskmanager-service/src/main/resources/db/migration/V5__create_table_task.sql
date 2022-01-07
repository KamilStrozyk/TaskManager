CREATE SEQUENCE sq_task START WITH 1 INCREMENT BY 1 CACHE 1 NO CYCLE;

CREATE TABLE task
(
    id           BIGINT       not null,
    task_list_id BIGINT       not null,
    title        VARCHAR(50)  not null,
    description  VARCHAR(200) null,
    finished     BOOLEAN      not null,
    created_at   DATE         not null
);

ALTER TABLE task
    ADD PRIMARY KEY (id);

ALTER TABLE task
    ADD CONSTRAINT fk_task_task_list FOREIGN KEY (task_list_id) REFERENCES task_list;