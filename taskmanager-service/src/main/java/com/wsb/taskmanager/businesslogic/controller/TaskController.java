package com.wsb.taskmanager.businesslogic.controller;

import com.wsb.taskmanager.businesslogic.dto.TaskDTO;
import com.wsb.taskmanager.businesslogic.exception.TaskListNotFoundException;
import com.wsb.taskmanager.businesslogic.exception.TaskNotFoundException;
import com.wsb.taskmanager.businesslogic.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Get tasks from given task list"
    )
    @Parameter(
            required = true,
            name = "listId",
            description = "Id of task list",
            in = ParameterIn.QUERY,
            example = "1"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All tasks of given task list",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TaskDTO.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Task list was not found",
                    content = @Content
            )
    })
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


    @Operation(
            summary = "Delete task"
    )
    @Parameter(
            required = true,
            name = "id",
            description = "Id of task to be removed",
            in = ParameterIn.PATH,
            example = "1"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task has been successfully deleted",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Task with given id does not exist",
                    content = @Content

            )
    })
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


    @Operation(
            summary = "Create new task and attach to given task list"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Task object created in web application",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = TaskDTO.class
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task has been created successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Task list with given id does not exist",
                    content = @Content
            )
    })
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

    @Operation(
            summary = "Update task"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Task object created in web application",
            content = @Content(
                    schema = @Schema(
                            implementation = TaskDTO.class
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task have been successfully updated",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Task with given id does not exist",
                    content = @Content
            )
    })
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

