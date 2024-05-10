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
@Table(name = "operation")
public class OperationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "operation_id")
    private List<DetailEntity> componentEntityList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private OrderEntity orderEntity;

    @OneToMany(cascade = CascadeType.ALL) // ???
    @JoinColumn(name = "operation_id")
    private List<TaskEntity> taskEntityList = new ArrayList<>();

    public void addComponent(DetailEntity componentEntity) {
        this.componentEntityList.add(componentEntity);
    }

    public void removeComponent(DetailEntity componentEntity) {
        this.componentEntityList.remove(componentEntity);
    }

    public void addTask(TaskEntity taskEntity) {
        this.taskEntityList.add(taskEntity);
    }

    public void removeTask(TaskEntity taskEntity) {
        this.taskEntityList.remove(taskEntity);
    }
}
