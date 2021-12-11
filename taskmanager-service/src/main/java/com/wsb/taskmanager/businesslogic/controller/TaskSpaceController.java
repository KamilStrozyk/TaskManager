package com.wsb.taskmanager.businesslogic.controller;

import com.wsb.taskmanager.businesslogic.dto.TaskSpaceDTO;
import com.wsb.taskmanager.businesslogic.exception.UserNotFoundException;
import com.wsb.taskmanager.businesslogic.service.TaskSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/task-space")
public class TaskSpaceController {

    private final TaskSpaceService taskSpaceService;

    @Autowired
    public TaskSpaceController(TaskSpaceService taskSpaceService) {
        this.taskSpaceService = taskSpaceService;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getTaskSpacesOfCurrentUser() {
        try {
            return ResponseEntity.ok()
                    .body(taskSpaceService.getTaskSpacesOfCurrentUser());
        } catch (UserNotFoundException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Set<TaskSpaceDTO> getAllTaskSpaces() {
        return taskSpaceService.getAllTaskSpaces();
    }

}
