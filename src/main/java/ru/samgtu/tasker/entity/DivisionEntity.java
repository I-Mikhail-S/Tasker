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
@AllArgsConstructor
@Entity
@Table(name = "division")
public class DivisionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "division_id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "division_id")
    private List<EmployeeEntity> employeeEntityList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "division_id")
    private List<InventoryEntity> inventoryEntityList = new ArrayList<>();

    @Transient
    private static final List<DivisionEntity> DIVISION_ENTITIES = new ArrayList<>();

    public DivisionEntity() {
        DIVISION_ENTITIES.add(this);
    }

    public DivisionEntity(List<EmployeeEntity> employeeEntityList, List<InventoryEntity> inventoryEntityList) {
        this.employeeEntityList = employeeEntityList;
        this.inventoryEntityList = inventoryEntityList;
        DIVISION_ENTITIES.add(this);
    }

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
