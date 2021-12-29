package com.wsb.taskmanager.businesslogic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wsb.taskmanager.authentication.model.UserBE;
import com.wsb.taskmanager.businesslogic.dto.TaskSpaceDTO;
import com.wsb.taskmanager.businesslogic.model.TaskSpaceBE;
import com.wsb.taskmanager.businesslogic.service.TaskSpaceService;
import com.wsb.taskmanager.utils.TaskSpaceTestFactory;
import com.wsb.taskmanager.utils.UserTestFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser(username = "Jack")
public class TaskSpaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskSpaceService taskSpaceService;

    @Test
    @Order(1)
    void shouldReturnStatus200IfTaskSpaceWasCreated() throws Exception {
        UserBE jack = UserTestFactory.createUserJack(null);
        TaskSpaceDTO taskSpace = TaskSpaceDTO
                .from(TaskSpaceTestFactory.createTaskSpaceWithGivenUser(jack));

        when(taskSpaceService.createTaskSpace(any())).thenReturn(taskSpace.getId());

        this.mockMvc.perform(post("/task-space/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(taskSpace)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void shouldReturnStatus200IfAllTaskSpacesOfCurrentUserWereFetched() throws Exception {
        UserBE jack = UserTestFactory.createUserJack(null);
        TaskSpaceBE taskSpace = TaskSpaceTestFactory.createTaskSpaceWithGivenUser(jack);
        TaskSpaceDTO taskSpaceDTO = TaskSpaceDTO.from(taskSpace);

        when(taskSpaceService.getTaskSpacesOfCurrentUser())
                .thenReturn(Collections.singleton(taskSpaceDTO));

        this.mockMvc.perform(get("/task-space"))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$", hasSize(1))))
                .andExpect(jsonPath("$[0].id", is(100)));
    }

    @Test
    @Order(3)
    void shouldReturn200IfTaskSpaceWasDeleted() throws Exception {
        UserBE jack = UserTestFactory.createUserJack(null);
        TaskSpaceBE taskSpace = TaskSpaceTestFactory.createTaskSpaceWithGivenUser(jack);

        doNothing().when(taskSpaceService).removeTaskSpace(taskSpace.getId());

        this.mockMvc.perform(delete("/task-space/remove/100"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void shouldReturn200IfTaskSpaceWasUpdated() throws Exception {
        UserBE jack = UserTestFactory.createUserJack(null);
        TaskSpaceBE taskSpace = TaskSpaceTestFactory.createTaskSpaceWithGivenUser(jack);

        doNothing().when(taskSpaceService).updateTaskSpace(any());

        this.mockMvc.perform(put("/task-space/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(TaskSpaceDTO.from(taskSpace))))
                .andExpect(status().isOk());
    }
}
