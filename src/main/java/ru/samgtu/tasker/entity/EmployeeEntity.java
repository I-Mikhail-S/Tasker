package ru.samgtu.tasker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.samgtu.tasker.api.type.EmployeeType;

import java.util.ArrayList;
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
    private List<DynamicTypeEntity> dynamicTypeEntityList = new ArrayList<>();

    @OneToOne
    private ScheduleEntity scheduleEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private StateEntity stateEntity;

    @Override
    public void perform() {

    }

    public void addDynamicType(DynamicTypeEntity dynamicTypeEntity) {
        this.dynamicTypeEntityList.add(dynamicTypeEntity);
    }

    public void removeDynamicType(DynamicTypeEntity dynamicTypeEntity) {
        this.dynamicTypeEntityList.remove(dynamicTypeEntity);
    }
}
