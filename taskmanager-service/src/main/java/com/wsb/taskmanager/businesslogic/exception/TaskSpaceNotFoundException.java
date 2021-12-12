package com.wsb.taskmanager.businesslogic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TaskSpaceNotFoundException extends Exception {
    public TaskSpaceNotFoundException() {
        super("Task space with given id was not found");
    }
}
