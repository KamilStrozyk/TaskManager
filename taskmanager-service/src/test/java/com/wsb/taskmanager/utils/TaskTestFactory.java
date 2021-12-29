package com.wsb.taskmanager.utils;

import com.wsb.taskmanager.businesslogic.model.TaskBE;
import com.wsb.taskmanager.businesslogic.model.TaskListBE;

import java.util.Date;

public class TaskTestFactory {

    public static TaskBE createTask(TaskListBE taskList) {
        return createTask(100L,
                "TASK",
                new Date(),
                false,
                "MY TASK",
                taskList);
    }

    public static TaskBE createTask(long id, String title, Date createdAt, boolean finished, String description, TaskListBE taskList) {
        return TaskBE.Builder.aTask()
                .withId(id)
                .withTitle(title)
                .withCreatedAt(createdAt)
                .withFinished(finished)
                .withDescription(description)
                .withTaskList(taskList)
                .build();
    }
}
