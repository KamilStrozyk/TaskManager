package com.wsb.taskmanager.businesslogic.controller;

import com.wsb.taskmanager.businesslogic.dto.TaskListDTO;
import com.wsb.taskmanager.businesslogic.exception.TaskListNotFoundException;
import com.wsb.taskmanager.businesslogic.exception.TaskSpaceNotFoundException;
import com.wsb.taskmanager.businesslogic.service.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Set<TaskListDTO> getAllTaskLists() {
        return taskListService.getAllTaskLists();
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getTaskListsBySpaceId(@RequestParam(name = "spaceId") long spaceId) {
        try {
            Set<TaskListDTO> taskLists = taskListService.getTaskListsBySpaceId(spaceId);
            return ResponseEntity.ok().body(taskLists);
        } catch (TaskSpaceNotFoundException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> removeTaskList(@PathVariable long id) {
        try {
            taskListService.removeTaskList(id);
            return ResponseEntity.ok().build();
        } catch (TaskListNotFoundException e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateTaskList(@Valid @RequestBody TaskListDTO taskListDTO) {
        try {
            taskListService.updateTaskList(taskListDTO);
            return ResponseEntity.ok().build();
        } catch (TaskListNotFoundException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createTaskList(@Valid @RequestBody TaskListDTO taskListDTO) {
        try {
            long id = taskListService.createTaskList(taskListDTO);
            return ResponseEntity.ok().body(id);
        } catch (TaskSpaceNotFoundException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
