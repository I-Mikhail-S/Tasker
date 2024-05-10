package ru.samgtu.tasker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.samgtu.tasker.entity.MachineEntity;

import java.util.Optional;

public interface MachineRepository extends JpaRepository<MachineEntity, Long> {
    Optional<MachineEntity> findBySerialNumber(String serialNumber);
}
