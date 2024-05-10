package ru.samgtu.tasker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shedule")
public class SheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shedule_id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shedule_id")
    private List<TaskEntity> taskEntityList = new ArrayList<>();

    public void addTask(TaskEntity taskEntity) {
        this.taskEntityList.add(taskEntity);
    }

    public void removeTask(TaskEntity taskEntity) {
        this.taskEntityList.remove(taskEntity);
    }
}
