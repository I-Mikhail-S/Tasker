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
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "orders_id")
    private List<OperationEntity> operationEntityList = new ArrayList<>();

    public void addOperation(OperationEntity operationEntity) {
        this.operationEntityList.add(operationEntity);
    }

    public void removeOperation(OperationEntity operationEntity) {
        this.operationEntityList.remove(operationEntity);
    }
}
