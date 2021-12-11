package com.wsb.taskmanager.businesslogic.dto;

import com.wsb.taskmanager.businesslogic.model.TaskSpaceBE;

import java.util.Date;
import java.util.Objects;

public class TaskSpaceDTO {
    private Long id;
    private String title;
    private Date createdAt;

    public TaskSpaceDTO() {
    }

    public TaskSpaceDTO(Long id, String title, Date createdAt) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public static TaskSpaceDTO from(TaskSpaceBE taskSpaceBE) {
        TaskSpaceDTO taskSpaceDTO = new TaskSpaceDTO();
        taskSpaceDTO.setId(taskSpaceBE.getId());
        taskSpaceDTO.setCreatedAt(taskSpaceBE.getCreatedAt());
        taskSpaceDTO.setTitle(taskSpaceBE.getTitle());
        return taskSpaceDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskSpaceDTO that = (TaskSpaceDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, createdAt);
    }
}
