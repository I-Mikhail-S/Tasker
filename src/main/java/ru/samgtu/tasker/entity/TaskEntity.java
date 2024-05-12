package ru.samgtu.tasker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.samgtu.tasker.service.ScheduleService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "shedule_id")
    private SheduleEntity sheduleEntity;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    @Column(name = "requaired")
    private List<SkillLevel> requiredSkillLevelList = new ArrayList<>();

    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;

    private Boolean isScheduled;

    // нужно добвить проверки (можно позже):
    // есть ли у исполнителя должная квалификация
    // соотнести skillLevel у employee с required skillLevel

    // соответствует ли тип Skill у inventory (Machine) со Skill из skillLevel
}
