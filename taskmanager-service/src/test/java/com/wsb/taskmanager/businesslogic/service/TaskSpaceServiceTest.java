package com.wsb.taskmanager.businesslogic.service;

import com.wsb.taskmanager.authentication.model.UserBE;
import com.wsb.taskmanager.authentication.repository.UserRepository;
import com.wsb.taskmanager.businesslogic.AuthenticationTestSetup;
import com.wsb.taskmanager.businesslogic.dto.TaskSpaceDTO;
import com.wsb.taskmanager.businesslogic.exception.TaskSpaceNotFoundException;
import com.wsb.taskmanager.businesslogic.exception.UserNotFoundException;
import com.wsb.taskmanager.businesslogic.model.TaskSpaceBE;
import com.wsb.taskmanager.businesslogic.repository.TaskSpaceRepository;
import com.wsb.taskmanager.utils.TaskSpaceTestFactory;
import com.wsb.taskmanager.utils.UserTestFactory;
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
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskSpaceServiceTest extends AuthenticationTestSetup {

    @Mock
    private TaskSpaceRepository taskSpaceRepository;

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<TaskSpaceBE> taskSpaceArgumentCaptor;

    @InjectMocks
    private TaskSpaceService taskSpaceService;

    @Test
    void shouldReturnAllTaskSpacesOfCurrentUser() throws UserNotFoundException {
        //given
        UserBE currentUser = UserTestFactory.createUserJack(null);
        TaskSpaceBE taskSpace = TaskSpaceTestFactory.createTaskSpaceWithGivenUser(currentUser);
        currentUser.setTaskSpaces(Collections.singleton(taskSpace));

        when(userRepository.findById(any())).thenReturn(Optional.of(currentUser));

        //when
        Set<TaskSpaceDTO> result = taskSpaceService.getTaskSpacesOfCurrentUser();

        //then
        assertThat(result.contains(TaskSpaceDTO.from(taskSpace))).isTrue();
    }

    @Test
    void shouldCreateTaskSpaceForCurrentUser() throws UserNotFoundException {
        //given
        UserBE currentUser = UserTestFactory.createUserJack(null);
        TaskSpaceBE taskSpace = TaskSpaceTestFactory.createTaskSpaceWithGivenUser(currentUser);
        currentUser.setTaskSpaces(Collections.singleton(taskSpace));

        when(userRepository.findById(any())).thenReturn(Optional.of(currentUser));
        when(taskSpaceRepository.save(any())).thenReturn(taskSpace);

        //when
        long id = taskSpaceService.createTaskSpace(TaskSpaceDTO.from(taskSpace));

        //then
        assertThat(id).isEqualTo(taskSpace.getId());
    }

    @Test
    void shouldThrowExceptionWhenUserWasNotFound() {
        //given
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> taskSpaceService.getTaskSpacesOfCurrentUser());

        //then
        assertThat(throwable).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void shouldRemoveTaskSpaceWithGivenId() throws TaskSpaceNotFoundException {
        //given
        UserBE currentUser = UserTestFactory.createUserJack(null);
        TaskSpaceBE taskSpace = TaskSpaceTestFactory.createTaskSpaceWithGivenUser(currentUser);

        when(taskSpaceRepository.findById(any())).thenReturn(Optional.of(taskSpace));

        //when
        taskSpaceService.removeTaskSpace(taskSpace.getId());

        //then
        verify(taskSpaceRepository).delete(taskSpaceArgumentCaptor.capture());
        assertThat(taskSpaceArgumentCaptor.getValue())
                .usingRecursiveComparison().isEqualTo(taskSpace);
    }

    @Test
    void shouldThrowExceptionWhenTaskSpaceWasNotFound() {
        //given
        when(taskSpaceRepository.findById(any())).thenReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> taskSpaceService.removeTaskSpace(1L));

        //then
        assertThat(throwable).isInstanceOf(TaskSpaceNotFoundException.class);
    }

    @Test
    void shouldUpdateTaskSpace() throws TaskSpaceNotFoundException {
        //given
        TaskSpaceBE currentTaskSpace = TaskSpaceTestFactory.createTaskSpaceWithGivenUser(null);
        TaskSpaceDTO updatedTaskSpace = TaskSpaceDTO.from(currentTaskSpace);
        updatedTaskSpace.setTitle("NEW TITLE");
        when(taskSpaceRepository.findById(any())).thenReturn(Optional.of(currentTaskSpace));

        //when
        taskSpaceService.updateTaskSpace(updatedTaskSpace);

        //then
        verify(taskSpaceRepository).save(taskSpaceArgumentCaptor.capture());
        assertThat(taskSpaceArgumentCaptor.getValue().getTitle())
                .isEqualTo(updatedTaskSpace.getTitle());
    }
}
