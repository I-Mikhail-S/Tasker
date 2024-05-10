package ru.samgtu.tasker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employeeEntity;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private InventoryEntity inventoryEntity;

    @OneToOne
    @Column(name = "requaired")
    private SkillLevel requairedSkillLevel;

    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;

    // нужно добвить проверки (можно позже):
    // есть ли у исполнителя должная квалификация
    // соотнести skillLevel у employee с required skillLevel

    // соответствует ли тип Skill у inventory (Machine) со Skill из skillLevel
}
