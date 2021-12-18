package com.wsb.taskmanager.businesslogic.controller;

import com.wsb.taskmanager.businesslogic.dto.TaskListDTO;
import com.wsb.taskmanager.businesslogic.service.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/task-list")
public class TaskListController {


    private final TaskListService taskListService;

    @Autowired
    public TaskListController(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Set<TaskListDTO> getAllTaskLists() {
        return taskListService.getAllTaskLists();
    }

}
