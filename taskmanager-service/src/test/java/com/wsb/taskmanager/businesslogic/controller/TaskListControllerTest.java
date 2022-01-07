package com.wsb.taskmanager.businesslogic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wsb.taskmanager.businesslogic.dto.TaskListDTO;
import com.wsb.taskmanager.businesslogic.model.TaskListBE;
import com.wsb.taskmanager.businesslogic.model.TaskSpaceBE;
import com.wsb.taskmanager.businesslogic.service.TaskListService;
import com.wsb.taskmanager.utils.TaskListTestFactory;
import com.wsb.taskmanager.utils.TaskSpaceTestFactory;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser(username = "Jack")
public class TaskListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskListService taskListService;

    @Test
    @Order(1)
    void shouldReturn200IfTaskListWasCreated() throws Exception {
        TaskSpaceBE taskSpace = TaskSpaceTestFactory.createTaskSpaceWithGivenUser(null);
        TaskListBE taskList = TaskListTestFactory.createTaskList(taskSpace, null);

        when(taskListService.createTaskList(any())).thenReturn(taskList.getId());

        this.mockMvc.perform(post("/task-list/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(TaskListDTO.from(taskList))))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void shouldReturn200IfTaskListsWereFetched() throws Exception {
        TaskSpaceBE taskSpace = TaskSpaceTestFactory.createTaskSpaceWithGivenUser(null);
        TaskListBE taskList = TaskListTestFactory.createTaskList(taskSpace, null);

        when(taskListService.getTaskListsBySpaceId(anyLong()))
                .thenReturn(Collections.singleton(TaskListDTO.from(taskList)));

        this.mockMvc.perform(get("/task-list?spaceId=100"))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$", hasSize(1))))
                .andExpect(jsonPath("$[0].id", is(1000)));

    }

    @Test
    @Order(3)
    void shouldReturn200IfTaskListWasDeleted() throws Exception {
        TaskSpaceBE taskSpace = TaskSpaceTestFactory.createTaskSpaceWithGivenUser(null);
        TaskListBE taskList = TaskListTestFactory.createTaskList(taskSpace, null);

        doNothing().when(taskListService).removeTaskList(taskList.getId());

        this.mockMvc.perform(delete("/task-list/remove/1000"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void shouldReturn200IfTaskListWasUpdated() throws Exception {
        TaskSpaceBE taskSpace = TaskSpaceTestFactory.createTaskSpaceWithGivenUser(null);
        TaskListBE taskList = TaskListTestFactory.createTaskList(taskSpace, null);

        doNothing().when(taskListService).updateTaskList(any());

        this.mockMvc.perform(put("/task-list/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(TaskListDTO.from(taskList))))
                .andExpect(status().isOk());
    }

}
