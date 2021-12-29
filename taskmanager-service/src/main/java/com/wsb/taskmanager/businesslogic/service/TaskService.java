package com.wsb.taskmanager.businesslogic.service;

import com.google.common.collect.Sets;
import com.wsb.taskmanager.businesslogic.dto.TaskDTO;
import com.wsb.taskmanager.businesslogic.exception.TaskListNotFoundException;
import com.wsb.taskmanager.businesslogic.exception.TaskNotFoundException;
import com.wsb.taskmanager.businesslogic.model.TaskBE;
import com.wsb.taskmanager.businesslogic.model.TaskListBE;
import com.wsb.taskmanager.businesslogic.repository.TaskListRepository;
import com.wsb.taskmanager.businesslogic.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskListRepository taskListRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    public Set<TaskDTO> getTasksByListId(long listId) throws TaskListNotFoundException {
        TaskListBE taskList = taskListRepository.findById(listId)
                .orElseThrow(TaskListNotFoundException::new);

        return Sets.newHashSet(taskList.getTasks())
                .stream()
                .sorted(Comparator.comparing(TaskBE::getCreatedAt))
                .map(TaskDTO::from)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void removeTask(long id) throws TaskNotFoundException {
        TaskBE task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        taskRepository.delete(task);
    }

    public long createTask(TaskDTO task) throws TaskListNotFoundException {
        TaskListBE taskList = taskListRepository.findById(task.getTaskListId())
                .orElseThrow(TaskListNotFoundException::new);

        TaskBE taskBE = TaskBE.Builder.aTask()
                .withTitle(task.getTitle())
                .withCreatedAt(task.getCreatedAt())
                .withDescription(task.getDescription())
                .withTaskList(taskList)
                .withFinished(task.isFinished())
                .build();

        taskBE = taskRepository.save(taskBE);

        return taskBE.getId();
    }

    public void updateTask(TaskDTO updatedTask) throws TaskNotFoundException {
        TaskBE task = taskRepository.findById(updatedTask.getId())
                .orElseThrow(TaskNotFoundException::new);

        task.setTitle(updatedTask.getTitle());
        task.setCreatedAt(updatedTask.getCreatedAt());
        task.setDescription(updatedTask.getDescription());
        task.setFinished(updatedTask.isFinished());
        // TODO Decide if task can be moved to another list
        task.setTaskList(taskListRepository.findById(updatedTask.getTaskListId()).get());
        taskRepository.save(task);
    }

}
