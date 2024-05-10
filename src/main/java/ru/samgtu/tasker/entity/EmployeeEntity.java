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
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private List<TypeEntity> typeEntityList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private DivisionEntity stateEntity;

    @OneToMany
    @JoinColumn(name = "task_id")
    private List<TaskEntity> taskEntityList = new ArrayList<>();

    public void addType(TypeEntity typeEntity) {
        this.typeEntityList.add(typeEntity);
    }

    public void removeType(TypeEntity typeEntity) {
        this.typeEntityList.remove(typeEntity);
    }

    public void addTask(TaskEntity taskEntity) {
        this.taskEntityList.add(taskEntity);
    }

    public void removeTask(TaskEntity taskEntity) {
        this.taskEntityList.remove(taskEntity);
    }
}
