package ru.samgtu.tasker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.samgtu.tasker.entity.EmployeeEntity;
import ru.samgtu.tasker.entity.InventoryEntity;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    Optional<InventoryEntity> findByName(String name);
}
