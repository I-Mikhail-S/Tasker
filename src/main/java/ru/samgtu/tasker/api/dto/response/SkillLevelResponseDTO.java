package ru.samgtu.tasker.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillLevelResponseDTO {
    private Long id;
    private String skill;
    private int laval;
}
