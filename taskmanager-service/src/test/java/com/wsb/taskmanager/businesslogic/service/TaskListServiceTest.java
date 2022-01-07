package com.wsb.taskmanager.businesslogic.service;

import com.wsb.taskmanager.businesslogic.dto.TaskListDTO;
import com.wsb.taskmanager.businesslogic.exception.TaskListNotFoundException;
import com.wsb.taskmanager.businesslogic.exception.TaskSpaceNotFoundException;
import com.wsb.taskmanager.businesslogic.model.TaskListBE;
import com.wsb.taskmanager.businesslogic.model.TaskSpaceBE;
import com.wsb.taskmanager.businesslogic.repository.TaskListRepository;
import com.wsb.taskmanager.businesslogic.repository.TaskSpaceRepository;
import com.wsb.taskmanager.utils.TaskListTestFactory;
import com.wsb.taskmanager.utils.TaskSpaceTestFactory;
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
public class TaskListServiceTest {

    @Mock
    private TaskListRepository taskListRepository;

    @Mock
    private TaskSpaceRepository taskSpaceRepository;

    @Captor
    private ArgumentCaptor<TaskListBE> taskListArgumentCaptor;

    @InjectMocks
    private TaskListService taskListService;


    @Test
    void shouldGetTaskListsFromGivenTaskSpace() throws TaskSpaceNotFoundException {
        //given
        TaskSpaceBE taskSpace = TaskSpaceTestFactory.createTaskSpaceWithGivenUser(null);
        TaskListBE taskList = TaskListTestFactory.createTaskList(taskSpace, null);
        taskSpace.setTaskList(Collections.singleton(taskList));

        when(taskSpaceRepository.findById(any())).thenReturn(Optional.of(taskSpace));

        //when
        Set<TaskListDTO> result = taskListService.getTaskListsBySpaceId(taskSpace.getId());

        //then
        assertThat(result.contains(TaskListDTO.from(taskList))).isTrue();
    }

    @Test
    void shouldRemoveTaskListWithGivenId() throws TaskListNotFoundException {
        //given
        TaskListBE taskList = TaskListTestFactory.createTaskList(null, null);

        when(taskListRepository.findById(any())).thenReturn(Optional.of(taskList));

        //when
        taskListService.removeTaskList(taskList.getId());

        //then
        verify(taskListRepository).delete(taskListArgumentCaptor.capture());
        assertThat(taskListArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(taskList);
    }

    @Test
    void shouldThrowExceptionWhenTaskListWithGivenIdWasNotFound() {
        //given
        when(taskListRepository.findById(any())).thenReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> taskListService.removeTaskList(1L));

        //then
        assertThat(throwable).isInstanceOf(TaskListNotFoundException.class);
    }

    @Test
    void shouldUpdateTaskListWithGivenId() throws TaskListNotFoundException {
        //given
        TaskListBE taskList = TaskListTestFactory
                .createTaskList(TaskSpaceTestFactory.createTaskSpaceWithGivenUser(null), null);
        TaskListDTO updatedTaskList = TaskListDTO.from(taskList);
        updatedTaskList.setTitle("NEW TITLE");

        when(taskListRepository.findById(any())).thenReturn(Optional.of(taskList));

        //when
        taskListService.updateTaskList(updatedTaskList);

        //then
        verify(taskListRepository).save(taskListArgumentCaptor.capture());
        assertThat(taskListArgumentCaptor.getValue().getTitle()).isEqualTo(updatedTaskList.getTitle());
    }

    @Test
    void shouldCreateTaskListAndAttachToGivenTaskSpace() throws TaskSpaceNotFoundException {
        //given
        TaskSpaceBE taskSpace = TaskSpaceTestFactory.createTaskSpaceWithGivenUser(null);
        TaskListBE taskList = TaskListTestFactory.createTaskList(taskSpace, null);
        taskSpace.setTaskList(Collections.singleton(taskList));

        when(taskSpaceRepository.findById(any())).thenReturn(Optional.of(taskSpace));
        when(taskListRepository.save(any())).thenReturn(taskList);

        //when
        long id = taskListService.createTaskList(TaskListDTO.from(taskList));

        //then
        assertThat(id).isEqualTo(taskList.getId());
    }

    @Test
    void shouldThrowExceptionWhenTaskSpaceWasNotFound() {
        //given
        when(taskSpaceRepository.findById(any())).thenReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> taskListService
                .createTaskList(TaskListDTO.from(TaskListTestFactory.createTaskList(TaskSpaceTestFactory
                        .createTaskSpaceWithGivenUser(null), null))));

        //then
        assertThat(throwable).isInstanceOf(TaskSpaceNotFoundException.class);
    }
}
