package ru.samgtu.tasker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.samgtu.tasker.api.dto.request.EmployeeCreateRequestDTO;
import ru.samgtu.tasker.api.dto.response.EmployeeResponseDTO;
import ru.samgtu.tasker.entity.EmployeeEntity;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    EmployeeResponseDTO toResponse(EmployeeEntity employee);

    EmployeeEntity toEntity(EmployeeCreateRequestDTO requestDTO);

}
