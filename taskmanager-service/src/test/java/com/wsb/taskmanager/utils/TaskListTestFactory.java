package com.wsb.taskmanager.utils;

import com.wsb.taskmanager.businesslogic.model.TaskBE;
import com.wsb.taskmanager.businesslogic.model.TaskListBE;
import com.wsb.taskmanager.businesslogic.model.TaskSpaceBE;

import java.util.Date;
import java.util.Set;

public class TaskListTestFactory {

    public static TaskListBE createTaskList(TaskSpaceBE taskSpace, Set<TaskBE> tasks) {
        return createTaskList(1000L,
                taskSpace,
                "TASK LIST",
                new Date(),
                tasks
        );
    }


    public static TaskListBE createTaskList(long id, TaskSpaceBE taskSpace, String title, Date createdAt, Set<TaskBE> tasks) {
        return TaskListBE.Builder.aTaskSpaceBE()
                .withId(id)
                .withTaskSpaceBE(taskSpace)
                .withTitle(title)
                .withCreatedAt(createdAt)
                .withTasks(tasks)
                .build();
    }


}
