package ru.samgtu.tasker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.samgtu.tasker.entity.EmployeeEntity;


public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

}
