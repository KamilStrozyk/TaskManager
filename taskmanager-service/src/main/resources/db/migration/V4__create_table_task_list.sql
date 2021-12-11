CREATE SEQUENCE sq_task_list START WITH 1 INCREMENT BY 1 CACHE 1 NO CYCLE;

CREATE TABLE task_list
(
    id            BIGINT      not null,
    task_space_id BIGINT      not null,
    title         VARCHAR(50) not null,
    created_at    DATE        not null
);

ALTER TABLE task_list
    ADD PRIMARY KEY (id);

ALTER TABLE task_list
    ADD CONSTRAINT fk_task_list_task_space FOREIGN KEY (task_space_id) REFERENCES task_space;