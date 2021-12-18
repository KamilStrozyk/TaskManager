package com.wsb.taskmanager.businesslogic.service;

import com.google.common.collect.Sets;
import com.wsb.taskmanager.businesslogic.dto.TaskListDTO;
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

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private TaskSpaceRepository taskSpaceRepository;

    public Set<TaskListDTO> getAllTaskLists() {
        return Sets.newHashSet(taskListRepository.findAll())
                .stream()
                .map(TaskListDTO::from)
                .collect(Collectors.toSet());
    }
}
