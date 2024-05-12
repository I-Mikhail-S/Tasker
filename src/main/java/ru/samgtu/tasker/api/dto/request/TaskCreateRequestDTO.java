package ru.samgtu.tasker.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskCreateRequestDTO {
    public Long employeeId;
    public Long inventoryId;
    public List<SkillLevelRequestDTO> skillLevelRequestDTOList;
    public LocalDateTime timeStart;
    public LocalDateTime timeEnd;
}
