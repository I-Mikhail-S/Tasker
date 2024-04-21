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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ExecutableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "executable_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "executable_id")
    private PurposeEntity purposeEntity;

    @OneToOne
    @JoinColumn(name = "executable_id")
    private TaskEntity taskEntity;
    public abstract void perform();
}
