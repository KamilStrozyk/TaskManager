package com.wsb.taskmanager.businesslogic.service;

import com.wsb.taskmanager.authentication.model.UserBE;
import com.wsb.taskmanager.authentication.repository.UserRepository;
import com.wsb.taskmanager.businesslogic.AuthenticationTestSetup;
import com.wsb.taskmanager.businesslogic.dto.TaskSpaceDTO;
import com.wsb.taskmanager.businesslogic.exception.UserNotFoundException;
import com.wsb.taskmanager.businesslogic.model.TaskSpaceBE;
import com.wsb.taskmanager.businesslogic.repository.TaskSpaceRepository;
import com.wsb.taskmanager.utils.TaskSpaceTestFactory;
import com.wsb.taskmanager.utils.UserTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskSpaceServiceTest extends AuthenticationTestSetup {

    @Mock
    private TaskSpaceRepository taskSpaceRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskSpaceService taskSpaceService;

    @Test
    @WithMockUser
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

}
