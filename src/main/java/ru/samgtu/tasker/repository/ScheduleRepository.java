package ru.samgtu.tasker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.samgtu.tasker.entity.SheduleEntity;
import ru.samgtu.tasker.mapper.ScheduleMapper;

public interface ScheduleRepository extends JpaRepository<SheduleEntity, Long> {
}
