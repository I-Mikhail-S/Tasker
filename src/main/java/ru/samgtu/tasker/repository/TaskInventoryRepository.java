package ru.samgtu.tasker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.samgtu.tasker.entity.TaskInventoryEntity;

public interface TaskInventoryRepository extends JpaRepository<TaskInventoryEntity,Long> {
}
