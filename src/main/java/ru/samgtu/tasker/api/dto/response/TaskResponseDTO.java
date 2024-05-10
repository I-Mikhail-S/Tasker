package ru.samgtu.tasker.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.samgtu.tasker.entity.SkillLevel;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponseDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private List<SkillLevel> employeeSkillLevel;
    private Long inventoryId;
    private String inventoryName;
    private List<SkillLevel> requiredSkillLevel;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
}
