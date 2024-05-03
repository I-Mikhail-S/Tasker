package ru.samgtu.tasker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.samgtu.tasker.api.type.MachineType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "machine")
public class MachineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_id")
    private Long id;

    private String serialNumber;

    private MachineType machineType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executable_id")
    private InventoryEntity inventoryEntity;
}
