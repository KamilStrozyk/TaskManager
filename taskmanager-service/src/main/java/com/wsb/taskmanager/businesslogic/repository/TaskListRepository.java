package com.wsb.taskmanager.businesslogic.repository;

import com.wsb.taskmanager.businesslogic.model.TaskListBE;
import org.springframework.data.repository.CrudRepository;

public interface TaskListRepository extends CrudRepository<TaskListBE, Long> {
}
