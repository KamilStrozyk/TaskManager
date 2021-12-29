package com.wsb.taskmanager.businesslogic.controller;

import com.wsb.taskmanager.businesslogic.dto.TaskListDTO;
import com.wsb.taskmanager.businesslogic.exception.TaskListNotFoundException;
import com.wsb.taskmanager.businesslogic.exception.TaskSpaceNotFoundException;
import com.wsb.taskmanager.businesslogic.service.TaskListService;
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
@RequestMapping("/task-list")
public class TaskListController {


    private final TaskListService taskListService;

    @Autowired
    public TaskListController(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    @Operation(
            summary = "Get task lists from given task space"
    )
    @Parameter(
            required = true,
            name = "spaceId",
            description = "Id of task space",
            in = ParameterIn.QUERY,
            example = "1"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All task lists of given task space",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TaskListDTO.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Task space was not found",
                    content = @Content
            )
    })
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


    @Operation(
            summary = "Delete task list"
    )
    @Parameter(
            required = true,
            name = "id",
            description = "Id of list to be removed",
            in = ParameterIn.PATH,
            example = "1"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task list has been successfully deleted",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Space with given id does not exist",
                    content = @Content

            )
    })
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


    @Operation(
            summary = "Update task list"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Task list object created in web application",
            content = @Content(
                    schema = @Schema(
                            implementation = TaskListDTO.class
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task list have been successfully updated",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Task list with given id does not exist",
                    content = @Content
            )
    })
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


    @Operation(
            summary = "Create new task list"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Task list object created in web application",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = TaskListDTO.class
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task list has been created successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Task space was not found",
                    content = @Content
            )
    })
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
