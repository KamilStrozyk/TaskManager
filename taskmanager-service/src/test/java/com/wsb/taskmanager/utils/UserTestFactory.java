package com.wsb.taskmanager.utils;

import com.wsb.taskmanager.authentication.model.UserBE;
import com.wsb.taskmanager.businesslogic.model.TaskSpaceBE;

import java.util.Set;

public class UserTestFactory {

    public static UserBE createUserJack(Set<TaskSpaceBE> taskSpaces) {
        return UserBE.Builder.aUserBE()
                .withId(1000L)
                .withEmail("jack@email.com")
                .withUsername("Jack")
                .withPassword("Jack")
                .withTaskSpaces(taskSpaces)
                .build();
    }

}
