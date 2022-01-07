package com.wsb.taskmanager.businesslogic.service;

import com.wsb.taskmanager.businesslogic.dto.TaskDTO;
import com.wsb.taskmanager.businesslogic.exception.TaskListNotFoundException;
import com.wsb.taskmanager.businesslogic.exception.TaskNotFoundException;
import com.wsb.taskmanager.businesslogic.model.TaskBE;
import com.wsb.taskmanager.businesslogic.model.TaskListBE;
import com.wsb.taskmanager.businesslogic.repository.TaskListRepository;
import com.wsb.taskmanager.businesslogic.repository.TaskRepository;
import com.wsb.taskmanager.utils.TaskListTestFactory;
import com.wsb.taskmanager.utils.TaskTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskListRepository taskListRepository;

    @Mock
    private TaskRepository taskRepository;

    @Captor
    private ArgumentCaptor<TaskBE> taskArgumentCaptor;

    @InjectMocks
    private TaskService taskService;

    @Test
    void shouldGetAllTasksOfGivenTaskList() throws TaskListNotFoundException {
        //given
        TaskListBE taskList = TaskListTestFactory.createTaskList(null, null);
        TaskBE task = TaskTestFactory.createTask(taskList);
        taskList.setTasks(Collections.singleton(task));

        when(taskListRepository.findById(any())).thenReturn(Optional.of(taskList));

        //when
        Set<TaskDTO> tasks = taskService.getTasksByListId(taskList.getId());

        //then
        assertThat(tasks.contains(TaskDTO.from(task))).isTrue();
    }
    
    @Test
    void shouldRemoveTaskWithGivenId() throws TaskNotFoundException {
        //when
        TaskBE task = TaskTestFactory.createTask(null);

        when(taskRepository.findById(any())).thenReturn(Optional.of(task));

        //when
        taskService.removeTask(task.getId());

        //then
        verify(taskRepository).delete(taskArgumentCaptor.capture());
        assertThat(taskArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(task);
    }

    @Test
    void shouldThrowExceptionWhenTaskWithGivenIdWasNotFound() {
        //given
        when(taskRepository.findById(any())).thenReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> taskService.removeTask(1L));

        //then
        assertThat(throwable).isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void shouldUpdateTaskWithGivenId() throws TaskNotFoundException {
        //given
        TaskListBE taskList = TaskListTestFactory.createTaskList(null, null);
        TaskBE task = TaskTestFactory.createTask(taskList);
        TaskDTO updatedTask = TaskDTO.from(task);
        updatedTask.setTitle("NEW TITLE");

        when(taskRepository.findById(any())).thenReturn(Optional.of(task));

        //when
        taskService.updateTask(updatedTask);

        //then
        verify(taskRepository).save(taskArgumentCaptor.capture());
        assertThat(taskArgumentCaptor.getValue().getTitle()).isEqualTo(updatedTask.getTitle());
    }

    @Test
    void shouldCreateTaskAndAttachToGivenTaskList() throws TaskListNotFoundException {
        //given
        TaskListBE taskList = TaskListTestFactory.createTaskList(null, null);
        TaskBE task = TaskTestFactory.createTask(taskList);

        when(taskListRepository.findById(any())).thenReturn(Optional.of(taskList));
        when(taskRepository.save(any())).thenReturn(task);

        //when
        long id = taskService.createTask(TaskDTO.from(task));

        //then
        assertThat(id).isEqualTo(task.getId());
    }
}
