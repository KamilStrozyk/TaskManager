package com.wsb.taskmanager.businesslogic.dto;

import com.sun.istack.NotNull;
import com.wsb.taskmanager.businesslogic.model.TaskListBE;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Objects;

public class TaskListDTO {

    private Long id;
    @NotNull
    private Long spaceId;
    @NotBlank
    private String title;
    @NotNull
    private Date createdAt;

    public TaskListDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
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

    public static TaskListDTO from(TaskListBE taskListBE) {
        TaskListDTO taskListDTO = new TaskListDTO();
        taskListDTO.setId(taskListBE.getId());
        taskListDTO.setTitle(taskListBE.getTitle());
        taskListDTO.setSpaceId(taskListBE.getTaskSpace().getId());
        taskListDTO.setCreatedAt(taskListBE.getCreatedAt());
        return taskListDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskListDTO that = (TaskListDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(spaceId, that.spaceId) && Objects.equals(title, that.title) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, spaceId, title, createdAt);
    }
}
