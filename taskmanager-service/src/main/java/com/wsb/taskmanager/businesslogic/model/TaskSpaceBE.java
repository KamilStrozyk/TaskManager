package com.wsb.taskmanager.businesslogic.model;

import com.wsb.taskmanager.authentication.model.UserBE;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "task_space", schema = "taskmanager")
@SequenceGenerator(name = TaskSpaceBE.SEQ_NAME, sequenceName = TaskSpaceBE.SEQ_NAME, allocationSize = 1)
public class TaskSpaceBE {
    public static final String SEQ_NAME = "sq_task_space";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TaskSpaceBE.SEQ_NAME)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserBE user;

    @Size(max = 50)
    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "taskSpace", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskListBE> taskList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserBE getUser() {
        return user;
    }

    public void setUser(UserBE user) {
        this.user = user;
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

    public Set<TaskListBE> getTaskList() {
        if (taskList == null) {
            taskList = new HashSet<>();
        }
        return taskList;
    }

    public void setTaskList(Set<TaskListBE> taskList) {
        this.taskList = taskList;
    }

    public static final class Builder {
        private long id;
        private UserBE user;
        private String title;
        private Date createdAt;
        private Set<TaskListBE> taskList;

        public Builder() {
        }

        public static Builder aTaskSpaceBE() {
            return new Builder();
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withUserBE(UserBE user) {
            this.user = user;
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

        public Builder withTaskList(Set<TaskListBE> taskList) {
            this.taskList = taskList;
            return this;
        }

        public TaskSpaceBE build() {
            TaskSpaceBE taskSpaceBE = new TaskSpaceBE();
            taskSpaceBE.setId(this.id);
            taskSpaceBE.setUser(this.user);
            taskSpaceBE.setTitle(this.title);
            taskSpaceBE.setCreatedAt(this.createdAt);
            taskSpaceBE.setTaskList(this.taskList);
            return taskSpaceBE;
        }
    }
}
