package com.wsb.taskmanager.businesslogic.repository;

import com.wsb.taskmanager.businesslogic.model.TaskSpaceBE;
import org.springframework.data.repository.CrudRepository;

public interface TaskSpaceRepository extends CrudRepository<TaskSpaceBE, Long> {
}
