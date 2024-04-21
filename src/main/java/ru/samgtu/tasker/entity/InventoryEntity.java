package ru.samgtu.tasker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id")
    private List<MachineEntity> machineEntityList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tool_id")
    private List<ToolEntity> toolEntityList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private StateEntity stateEntity;
    @Override
    public void perform() {

    }
}
