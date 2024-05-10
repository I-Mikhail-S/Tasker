package ru.samgtu.tasker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.samgtu.tasker.entity.TypeEntity;

public interface TypeRepository extends JpaRepository<TypeEntity, Long> {

}
