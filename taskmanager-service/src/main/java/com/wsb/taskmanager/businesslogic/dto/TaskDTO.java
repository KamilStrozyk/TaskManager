package com.wsb.taskmanager.businesslogic.dto;

import com.wsb.taskmanager.businesslogic.model.TaskBE;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class TaskDTO {

    private Long id;
    @NotNull
    private Long taskListId;
    @NotBlank
    private String title;

    private String description;
    @NotNull
    private boolean finished;
    @NotNull
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(Long taskListId) {
        this.taskListId = taskListId;
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

    public static TaskDTO from(TaskBE taskBE) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(taskBE.getId());
        taskDTO.setTitle(taskBE.getTitle());
        taskDTO.setTaskListId(taskBE.getTaskList().getId());
        taskDTO.setDescription(taskBE.getDescription());
        taskDTO.setFinished(taskBE.isFinished());
        taskDTO.setCreatedAt(taskBE.getCreatedAt());
        return taskDTO;
    }
}
