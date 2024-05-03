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
public class InventoryEntity extends ExecutableEntity {
    @Column(name = "inventory_id")
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "executable_id")
    private List<MachineEntity> machineEntityList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "executable_id")
    private List<ToolEntity> toolEntityList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private StateEntity stateEntity;
    @Override
    public void perform() {

    }

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
}
