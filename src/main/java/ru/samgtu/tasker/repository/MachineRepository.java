package ru.samgtu.tasker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.samgtu.tasker.entity.MachineEntity;

public interface MachineRepository extends JpaRepository<MachineEntity, Long> {
}
