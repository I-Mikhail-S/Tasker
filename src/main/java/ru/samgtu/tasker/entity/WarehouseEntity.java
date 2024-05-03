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
@Table(name = "warehouse")
public class WarehouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id")
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "warehouse_id")
    private List<ComponentEntity> componentEntityList = new ArrayList<>();

    public void addComponent(ComponentEntity componentEntity) {
        this.componentEntityList.add(componentEntity);
    }

    public void removeComponent(ComponentEntity componentEntity) {
        this.componentEntityList.remove(componentEntity);
    }
}
