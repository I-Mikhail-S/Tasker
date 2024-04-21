package ru.samgtu.tasker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.samgtu.tasker.api.type.EmployeeType;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class EmployeeEntity extends ExecutableEntity {
    @Column(name = "employee_id")
    private Long id;

    private String name;

    private EmployeeType employeeType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private List<DynamicTypeEntity> dynamicTypeEntityList;

    @OneToOne
    private ScheduleEntity scheduleEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private StateEntity stateEntity;

    @Override
    public void perform() {

    }
}
