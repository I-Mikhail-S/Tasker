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
@Table(name = "division")
public class DivisionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "division_id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "division_id")
    private List<StateEntity> stateEntityList = new ArrayList<>();

    public void addState(StateEntity stateEntity) {
        this.stateEntityList.add(stateEntity);
    }

    public void removeState(StateEntity stateEntity) {
        this.stateEntityList.remove(stateEntity);
    }
}
