package ru.samgtu.tasker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.samgtu.tasker.api.dto.request.TaskCreateRequestDTO;
import ru.samgtu.tasker.api.dto.response.TaskResponseDTO;
import ru.samgtu.tasker.entity.TaskEntity;

import java.util.List;

@Mapper
public interface TaskMapper {
    @Mapping(target = "employeeId", source = "employeeEntity.id")
    @Mapping(target = "employeeName", source = "employeeEntity.name")
    @Mapping(target = "employeeSkillLevel", source = "employeeEntity.skillLevelList")
    @Mapping(target = "inventoryId", source = "inventoryEntity.id")
    @Mapping(target = "inventoryName", source = "inventoryEntity.name")
    @Mapping(target = "requiredSkillLevel", source = "requiredSkillLevelList")
    TaskResponseDTO toResponse(TaskEntity taskEntity);

    List<TaskResponseDTO> toResponseList(List<TaskEntity> taskEntityList);

    @Mapping(target = "employeeEntity.id", source = "employeeId")
    @Mapping(target = "inventoryEntity.id", source = "inventoryId")
    TaskEntity toEntity(TaskCreateRequestDTO request);
}
