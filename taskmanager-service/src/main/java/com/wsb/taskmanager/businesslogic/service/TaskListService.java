package com.wsb.taskmanager.businesslogic.service;

import com.google.common.collect.Sets;
import com.wsb.taskmanager.businesslogic.dto.TaskListDTO;
import com.wsb.taskmanager.businesslogic.exception.TaskListNotFoundException;
import com.wsb.taskmanager.businesslogic.exception.TaskSpaceNotFoundException;
import com.wsb.taskmanager.businesslogic.model.TaskListBE;
import com.wsb.taskmanager.businesslogic.model.TaskSpaceBE;
import com.wsb.taskmanager.businesslogic.repository.TaskListRepository;
import com.wsb.taskmanager.businesslogic.repository.TaskSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskListService {

    private final TaskListRepository taskListRepository;

    private final TaskSpaceRepository taskSpaceRepository;

    @Autowired
    public TaskListService(TaskListRepository taskListRepository, TaskSpaceRepository taskSpaceRepository) {
        this.taskListRepository = taskListRepository;
        this.taskSpaceRepository = taskSpaceRepository;
    }

    public Set<TaskListDTO> getTaskListsBySpaceId(long spaceId) throws TaskSpaceNotFoundException {
        TaskSpaceBE taskSpace = taskSpaceRepository.findById(spaceId)
                .orElseThrow(TaskSpaceNotFoundException::new);

        return Sets.newHashSet(taskSpace.getTaskList())
                .stream()
                .map(TaskListDTO::from)
                .collect(Collectors.toSet());
    }

    public void removeTaskList(long id) throws TaskListNotFoundException {
        TaskListBE taskList = taskListRepository.findById(id)
                .orElseThrow(TaskListNotFoundException::new);

        taskListRepository.delete(taskList);
    }

    public void updateTaskList(TaskListDTO updatedTaskList) throws TaskListNotFoundException {
        TaskListBE taskList = taskListRepository.findById(updatedTaskList.getId())
                .orElseThrow(TaskListNotFoundException::new);

        taskList.setTitle(updatedTaskList.getTitle());
        taskList.setCreatedAt(updatedTaskList.getCreatedAt());
        // TODO Think about changing task space
//        taskList.setTaskSpace(updatedTaskList.getSpaceId());
        taskListRepository.save(taskList);
    }

    public long createTaskList(TaskListDTO taskList) throws TaskSpaceNotFoundException {
        TaskSpaceBE taskSpace = taskSpaceRepository.findById(taskList.getSpaceId())
                .orElseThrow(TaskSpaceNotFoundException::new);

        TaskListBE taskListBE = TaskListBE.Builder.aTaskSpaceBE()
                .withTaskSpaceBE(taskSpace)
                .withTitle(taskList.getTitle())
                .withCreatedAt(taskList.getCreatedAt())
                .build();

        taskListBE = taskListRepository.save(taskListBE);

        return taskListBE.getId();
    }
}
