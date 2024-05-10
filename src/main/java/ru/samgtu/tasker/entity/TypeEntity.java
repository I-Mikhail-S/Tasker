package ru.samgtu.tasker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "type_skill")
public class TypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_skill_id")
    private Long id;

    @Column(name = "name_skill")
    private String skill;

    @ManyToOne
    @JoinColumn(name = "machine_id")
    private MachineEntity machineEntity;

    @OneToOne
    @JoinColumn(name = "skill_level_id")
    private SkillLevel skillLevel;
}
