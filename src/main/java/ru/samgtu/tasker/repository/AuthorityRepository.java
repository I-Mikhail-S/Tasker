package ru.samgtu.tasker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.samgtu.tasker.entity.AuthorityEntity;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Integer> {
    Optional<AuthorityEntity> findByAuthority(String authority);
}
