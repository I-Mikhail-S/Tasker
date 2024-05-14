package ru.samgtu.tasker.service;

import org.springframework.stereotype.Service;
import ru.samgtu.tasker.api.dto.request.EmployeeCreateRequestDTO;
import ru.samgtu.tasker.api.dto.request.SkillLevelRequestDTO;
import ru.samgtu.tasker.api.dto.response.EmployeeResponseDTO;
import ru.samgtu.tasker.api.dto.response.TaskResponseDTO;
import ru.samgtu.tasker.entity.EmployeeEntity;
import ru.samgtu.tasker.entity.SkillLevel;
import ru.samgtu.tasker.entity.TaskEntity;
import ru.samgtu.tasker.entity.TypeEntity;
import ru.samgtu.tasker.mapper.EmployeeMapper;
import ru.samgtu.tasker.mapper.TaskMapper;
import ru.samgtu.tasker.repository.EmployeeRepository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final TaskMapper taskMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, TaskMapper taskMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.taskMapper = taskMapper;
    }

    public List<EmployeeResponseDTO> findAllEmployees() {
        List<EmployeeEntity> employeeEntityList = employeeRepository.findAll();
        List<EmployeeResponseDTO> employeeResponseDTOList = new ArrayList<>();
        for (EmployeeEntity employee : employeeEntityList) {
            EmployeeResponseDTO responseDTO = employeeMapper.toResponse(employee);
            responseDTO.setTaskEntityList(taskMapper.toResponseList(employee.getTaskEntityList()));
            employeeResponseDTOList.add(responseDTO);
        }

        return employeeResponseDTOList;
    }

    public EmployeeResponseDTO createEmployee(EmployeeCreateRequestDTO request) {
        EmployeeEntity employee = employeeMapper.toEntity(request);
        List<SkillLevel> skillLevelList = new ArrayList<>();
        for (SkillLevelRequestDTO skillLevelRequest : request.getSkillLevelRequestDTOList()) {
            SkillLevel skillLevel = new SkillLevel();
            TypeEntity type = new TypeEntity();
            skillLevel.setLevel(skillLevelRequest.getLevel());
            type.setSkill(skillLevelRequest.getSkill());
            skillLevel.setSkill(type);
        }

        employee.setSkillLevelList(skillLevelList);
        return employeeMapper.toResponse(employeeRepository.save(employee));
    }
}
