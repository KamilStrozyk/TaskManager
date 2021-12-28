package com.wsb.taskmanager.utils;

import com.google.common.collect.Sets;
import com.wsb.taskmanager.authentication.model.UserBE;
import com.wsb.taskmanager.businesslogic.model.TaskListBE;
import com.wsb.taskmanager.businesslogic.model.TaskSpaceBE;

import java.util.Date;
import java.util.Set;

public class TaskSpaceTestFactory {

    public static TaskSpaceBE createTaskSpaceWithGivenUser(UserBE user) {
        return createTaskSpace(100L,
                user,
                "Task space",
                new Date(),
                null
        );
    }

    public static TaskSpaceBE createTaskSpace(long id, UserBE user, String title, Date createdAt, Set<TaskListBE> taskList) {

        return TaskSpaceBE.Builder.aTaskSpaceBE()
                .withId(id)
                .withUserBE(user)
                .withTitle(title)
                .withCreatedAt(createdAt)
                .withTaskList(taskList)
                .build();
    }

}
