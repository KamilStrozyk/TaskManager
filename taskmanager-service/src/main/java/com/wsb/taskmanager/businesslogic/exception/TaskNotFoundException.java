package com.wsb.taskmanager.businesslogic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TaskNotFoundException extends Exception{
    public TaskNotFoundException() {
        super("Task with given id was not found");
    }
}
