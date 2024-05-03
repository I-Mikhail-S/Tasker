package ru.samgtu.tasker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.samgtu.tasker.entity.ToolEntity;

public interface ToolRepository extends JpaRepository<ToolEntity, Long> {
}
