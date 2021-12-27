package com.wsb.taskmanager.businesslogic.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "task_list", schema = "taskmanager")
@SequenceGenerator(name = TaskListBE.SEQ_NAME, sequenceName = TaskListBE.SEQ_NAME, allocationSize = 1)
public class TaskListBE {
    public static final String SEQ_NAME = "sq_task_list";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TaskListBE.SEQ_NAME)
    private long id;

    @ManyToOne
    @JoinColumn(name = "task_space_id")
    private TaskSpaceBE taskSpace;

    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskBE> tasks;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TaskSpaceBE getTaskSpace() {
        return taskSpace;
    }

    public void setTaskSpace(TaskSpaceBE taskSpace) {
        this.taskSpace = taskSpace;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<TaskBE> getTasks() {
        if (tasks == null) {
            tasks = new HashSet<>();
        }
        return tasks;
    }

    public void setTasks(Set<TaskBE> tasks) {
        this.tasks = tasks;
    }

    public void addTask(TaskBE task) {
        getTasks().add(task);
    }

    public static final class Builder {
        private long id;
        private TaskSpaceBE taskSpace;
        private String title;
        private Date createdAt;
        private Set<TaskBE> tasks;

        public Builder() {
        }

        public static Builder aTaskSpaceBE() {
            return new Builder();
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withTaskSpaceBE(TaskSpaceBE taskSpace) {
            this.taskSpace = taskSpace;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withTasks(Set<TaskBE> tasks) {
            this.tasks = tasks;
            return this;
        }

        public TaskListBE build() {
            TaskListBE taskListBE = new TaskListBE();
            taskListBE.setId(this.id);
            taskListBE.setTaskSpace(this.taskSpace);
            taskListBE.setTitle(this.title);
            taskListBE.setCreatedAt(this.createdAt);
            taskListBE.setTasks(this.tasks);
            return taskListBE;
        }
    }
}
