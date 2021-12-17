package com.wsb.taskmanager.businesslogic.exception;

public class TaskListNotFoundException extends Exception {
    public TaskListNotFoundException() {
        super("Task list with given id was not found");
    }
}
