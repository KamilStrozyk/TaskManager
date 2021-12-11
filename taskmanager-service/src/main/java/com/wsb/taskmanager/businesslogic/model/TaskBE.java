package com.wsb.taskmanager.businesslogic.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "task", schema = "taskmanager")
@SequenceGenerator(name = TaskBE.SEQ_NAME, sequenceName = TaskBE.SEQ_NAME, allocationSize = 1)
public class TaskBE {

    public static final String SEQ_NAME = "sq_task";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TaskBE.SEQ_NAME)
    private long id;

    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_list_id")
    private TaskListBE taskList;

    @NotBlank
    @Size(max = 50)
    @Column(name = "title")
    private String title;

    @Null
    @Size(max = 200)
    @Column(name = "description")
    private String description;

    @NotBlank
    @Column(name = "finished")
    private boolean finished;

    @NotBlank
    @Column(name = "created_at")
    private Date createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TaskListBE getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskListBE taskList) {
        this.taskList = taskList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public static final class Builder {
        private long id;
        private TaskListBE taskList;
        private String title;
        private String description;
        private boolean finished;
        private Date createdAt;

        public Builder() {
        }

        public static Builder aTask() {
            return new Builder();
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withTaskList(TaskListBE taskList) {
            this.taskList = taskList;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withFinished(boolean finished) {
            this.finished = finished;
            return this;
        }

        public Builder withCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public TaskBE build() {
            TaskBE taskBE = new TaskBE();
            taskBE.setId(this.id);
            taskBE.setTaskList(this.taskList);
            taskBE.setTitle(this.title);
            taskBE.setDescription(this.description);
            taskBE.setFinished(this.finished);
            taskBE.setCreatedAt(this.createdAt);
            return taskBE;
        }
    }
}
