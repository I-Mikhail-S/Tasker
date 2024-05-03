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
@Table(name = "state")
public class StateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "state_id")
    private List<EmployeeEntity> employeeEntityList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "state_id")
    private List<InventoryEntity> inventoryEntityList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "division_id")
    private DivisionEntity divisionEntity;

    public void addEmployee(EmployeeEntity employeeEntity) {
        this.employeeEntityList.add(employeeEntity);
    }

    public void removeEmployee(EmployeeEntity employeeEntity) {
        this.employeeEntityList.remove(employeeEntity);
    }

    public void addInventory(InventoryEntity inventoryEntity) {
        this.inventoryEntityList.add(inventoryEntity);
    }

    public void removeInventory(InventoryEntity inventoryEntity) {
        this.inventoryEntityList.remove(inventoryEntity);
    }
}
