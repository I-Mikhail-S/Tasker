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
@Table(name = "schedule_employee")
public class ScheduleEmployeeEntity extends ScheduleEntity {
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id")
    private List<TaskEmployeeEntity> taskEmployeeEntityList = new ArrayList<>();

    public void addTaskEmployee(TaskEmployeeEntity taskEmployeeEntity) {
        this.taskEmployeeEntityList.add(taskEmployeeEntity);
    }

    public void removeTaskEmployee(TaskEmployeeEntity taskEmployeeEntity) {
        this.taskEmployeeEntityList.remove(taskEmployeeEntity);
    }
}
