package com.wsb.taskmanager.businesslogic.service;

import com.google.common.collect.Sets;
import com.wsb.taskmanager.authentication.model.UserBE;
import com.wsb.taskmanager.authentication.repository.UserRepository;
import com.wsb.taskmanager.authentication.security.service.UserDetailsImpl;
import com.wsb.taskmanager.businesslogic.dto.TaskSpaceDTO;
import com.wsb.taskmanager.businesslogic.exception.TaskSpaceNotFoundException;
import com.wsb.taskmanager.businesslogic.exception.UserNotFoundException;
import com.wsb.taskmanager.businesslogic.model.TaskSpaceBE;
import com.wsb.taskmanager.businesslogic.repository.TaskSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskSpaceService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskSpaceRepository taskSpaceRepository;

    public Set<TaskSpaceDTO> getAllTaskSpaces() {
        return Sets.newHashSet(taskSpaceRepository.findAll())
                .stream()
                .map(TaskSpaceDTO::from)
                .collect(Collectors.toSet());
    }

    public Set<TaskSpaceDTO> getTaskSpacesOfCurrentUser() throws UserNotFoundException {
        UserBE currentUser = getCurrentUser().orElseThrow(UserNotFoundException::new);

        return currentUser.getTaskSpaces()
                .stream()
                .map(TaskSpaceDTO::from)
                .collect(Collectors.toSet());
    }

    public long createTaskSpace(TaskSpaceDTO taskSpace) throws UserNotFoundException {
        UserBE currentUser = getCurrentUser().orElseThrow(UserNotFoundException::new);

        TaskSpaceBE taskSpaceBE = TaskSpaceBE.Builder.aTaskSpaceBE()
                .withUserBE(currentUser)
                .withTitle(taskSpace.getTitle())
                .withCreatedAt(taskSpace.getCreatedAt())
                .build();

        taskSpaceBE = taskSpaceRepository.save(taskSpaceBE);

        return taskSpaceBE.getId();
    }

    public void removeTaskSpace(long id) throws TaskSpaceNotFoundException {
        TaskSpaceBE taskSpace = taskSpaceRepository.findById(id)
                .orElseThrow(TaskSpaceNotFoundException::new);

        taskSpaceRepository.delete(taskSpace);
    }

    public void updateTaskSpace(TaskSpaceDTO updatedTaskSpace) throws TaskSpaceNotFoundException {
        TaskSpaceBE taskSpace = taskSpaceRepository.findById(updatedTaskSpace.getId())
                .orElseThrow(TaskSpaceNotFoundException::new);

        taskSpace.setTitle(updatedTaskSpace.getTitle());
        taskSpace.setCreatedAt(updatedTaskSpace.getCreatedAt());

        taskSpaceRepository.save(taskSpace);
    }

    private Optional<UserBE> getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return userRepository.findById(userDetails.getId());
    }
}
