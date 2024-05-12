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
@Table(name = "inventory")
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private List<MachineEntity> machineEntityList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private List<ToolEntity> toolEntityList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private List<TaskEntity> taskEntityList = new ArrayList<>();

    public void addMachine(MachineEntity machineEntity) {
        this.machineEntityList.add(machineEntity);
    }

    public void removeMachine(MachineEntity machineEntity) {
        this.machineEntityList.remove(machineEntity);
    }

    public void addTool(ToolEntity toolEntity) {
        this.toolEntityList.add(toolEntity);
    }

    public void removeTool(ToolEntity toolEntity) {
        this.toolEntityList.remove(toolEntity);
    }

    public void addTask(TaskEntity taskEntity) {
        this.taskEntityList.add(taskEntity);
    }

    public void removeTask(TaskEntity taskEntity) {
        this.taskEntityList.remove(taskEntity);
    }
}
