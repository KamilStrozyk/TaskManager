package com.wsb.taskmanager.businesslogic.dto;

import com.sun.istack.NotNull;
import com.wsb.taskmanager.businesslogic.model.TaskListBE;

import javax.validation.constraints.NotBlank;
import java.util.Date;

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

    public TaskListDTO(Long id, Long spaceId, String title, Date createdAt) {
        this.id = id;
        this.spaceId = spaceId;
        this.title = title;
        this.createdAt = createdAt;
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
        return taskListDTO;

    }
}
