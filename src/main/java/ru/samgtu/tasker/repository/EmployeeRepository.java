package ru.samgtu.tasker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.samgtu.tasker.entity.EmployeeEntity;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    Optional<EmployeeEntity> findByName(String name);
}
