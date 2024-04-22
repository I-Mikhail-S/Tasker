package ru.samgtu.tasker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.samgtu.tasker.entity.TaskEmployeeEntity;

public interface TaskEmployeeRepository extends JpaRepository<TaskEmployeeEntity, Long> {
}
