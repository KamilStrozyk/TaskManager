package com.wsb.taskmanager.businesslogic.service;

import com.wsb.taskmanager.businesslogic.model.TaskBE;
import com.wsb.taskmanager.businesslogic.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Set<TaskBE> getAllTasksOfTaskList(long id) {
        return null;
    }
}
