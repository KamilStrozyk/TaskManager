package com.wsb.taskmanager.businesslogic.controller;

import com.wsb.taskmanager.businesslogic.dto.TaskSpaceDTO;
import com.wsb.taskmanager.businesslogic.exception.TaskSpaceNotFoundException;
import com.wsb.taskmanager.businesslogic.exception.UserNotFoundException;
import com.wsb.taskmanager.businesslogic.service.TaskSpaceService;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/task-space")
public class TaskSpaceController {

    private final TaskSpaceService taskSpaceService;

    @Autowired
    public TaskSpaceController(TaskSpaceService taskSpaceService) {
        this.taskSpaceService = taskSpaceService;
    }

    @Operation(
            summary = "Get task spaces of current user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All spaces of currently logged in user",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TaskSpaceDTO.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "User was not found",
                    content = @Content
            )
    })
    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getTaskSpacesOfCurrentUser() {
        try {
            return ResponseEntity.ok().body(taskSpaceService.getTaskSpacesOfCurrentUser());
        } catch (UserNotFoundException e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }


    @Operation(
            summary = "Create new task space"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Task space object created in web application",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = TaskSpaceDTO.class
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Space has been created successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "User was not found",
                    content = @Content
            )
    })
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createTaskSpace(@Valid @RequestBody TaskSpaceDTO taskSpace) {
        try {
            long id = taskSpaceService.createTaskSpace(taskSpace);
            return ResponseEntity.ok().body(id);
        } catch (UserNotFoundException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(
            summary = "Delete task space"
    )
    @Parameter(
            required = true,
            name = "id",
            description = "Id of space to be removed",
            in = ParameterIn.PATH,
            example = "1"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task space has been successfully deleted",
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
    public ResponseEntity<?> removeTaskSpace(@PathVariable long id) {
        try {
            taskSpaceService.removeTaskSpace(id);
            return ResponseEntity.ok().build();
        } catch (TaskSpaceNotFoundException e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @Operation(
            summary = "Update task space"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Task space object created in web application",
            content = @Content(
                    schema = @Schema(
                            implementation = TaskSpaceDTO.class
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task space have been successfully updated",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Task space with given id does not exist",
                    content = @Content
            )
    })
    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateTaskSpace(@Valid @RequestBody TaskSpaceDTO taskSpaceDTO) {
        try {
            taskSpaceService.updateTaskSpace(taskSpaceDTO);
            return ResponseEntity.ok().build();
        } catch (TaskSpaceNotFoundException e) {
            return ResponseEntity.internalServerError().body(e);
        }
    }
}
