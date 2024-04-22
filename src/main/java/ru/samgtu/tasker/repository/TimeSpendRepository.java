package ru.samgtu.tasker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.samgtu.tasker.entity.TimeSpendEntity;

public interface TimeSpendRepository extends JpaRepository<TimeSpendEntity, Long> {
}
