package com.wsb.taskmanager.businesslogic.controller;

import com.wsb.taskmanager.businesslogic.dto.TaskDTO;
import com.wsb.taskmanager.businesslogic.exception.TaskListNotFoundException;
import com.wsb.taskmanager.businesslogic.exception.TaskNotFoundException;
import com.wsb.taskmanager.businesslogic.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getTasksByListId(@RequestParam(name = "listId") long listId) {
        try {
            Set<TaskDTO> tasks = taskService.getTasksByListId(listId);
            return ResponseEntity.ok().body(tasks);
        } catch (TaskListNotFoundException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> removeTask(@PathVariable long id) {
        try {
            taskService.removeTask(id);
            return ResponseEntity.ok().build();
        } catch (TaskNotFoundException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        try {
            long id = taskService.createTask(taskDTO);
            return ResponseEntity.ok().body(id);
        } catch (TaskListNotFoundException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateTask(@Valid @RequestBody TaskDTO taskDTO) {
        try {
            taskService.updateTask(taskDTO);
            return ResponseEntity.ok().build();
        } catch (TaskNotFoundException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}

