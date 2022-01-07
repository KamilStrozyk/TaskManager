package com.wsb.taskmanager.businesslogic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wsb.taskmanager.businesslogic.dto.TaskDTO;
import com.wsb.taskmanager.businesslogic.model.TaskBE;
import com.wsb.taskmanager.businesslogic.model.TaskListBE;
import com.wsb.taskmanager.businesslogic.service.TaskService;
import com.wsb.taskmanager.utils.TaskListTestFactory;
import com.wsb.taskmanager.utils.TaskTestFactory;
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
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    @Order(1)
    void shouldReturn200IfTaskWasCreated() throws Exception {
        TaskListBE taskList = TaskListTestFactory.createTaskList(null, null);
        TaskBE task = TaskTestFactory.createTask(taskList);

        when(taskService.createTask(any())).thenReturn(task.getId());

        this.mockMvc.perform(post("/task/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(TaskDTO.from(task))))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void shouldReturn200IfTasksWereFetched() throws Exception {
        TaskListBE taskList = TaskListTestFactory.createTaskList(null, null);
        TaskBE task = TaskTestFactory.createTask(taskList);

        when(taskService.getTasksByListId(anyLong()))
                .thenReturn(Collections.singleton(TaskDTO.from(task)));

        this.mockMvc.perform(get("/task?listId=1000"))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$", hasSize(1))))
                .andExpect(jsonPath("$[0].id", is(100)));
    }

    @Test
    @Order(3)
    void shouldReturn200IfTaskWasDeleted() throws Exception {
        TaskBE task = TaskTestFactory.createTask(null);

        doNothing().when(taskService).removeTask(task.getId());

        this.mockMvc.perform(delete("/task/remove/100"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void shouldReturn200IfTaskWasUpdated() throws Exception {
        TaskListBE taskList = TaskListTestFactory.createTaskList(null, null);
        TaskBE task = TaskTestFactory.createTask(taskList);

        doNothing().when(taskService).updateTask(any());

        this.mockMvc.perform(put("/task/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(TaskDTO.from(task))))
                .andExpect(status().isOk());
    }

}
