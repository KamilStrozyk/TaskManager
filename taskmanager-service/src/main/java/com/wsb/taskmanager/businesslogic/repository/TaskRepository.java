package com.wsb.taskmanager.businesslogic.repository;

import com.wsb.taskmanager.businesslogic.model.TaskBE;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<TaskBE, Long> {
}
