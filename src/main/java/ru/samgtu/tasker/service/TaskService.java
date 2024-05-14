package ru.samgtu.tasker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.samgtu.tasker.api.dto.request.SkillLevelRequestDTO;
import ru.samgtu.tasker.api.dto.request.TaskCreateRequestDTO;
import ru.samgtu.tasker.api.dto.response.TaskResponseDTO;
import ru.samgtu.tasker.api.exception.NotFoundEmployeeException;
import ru.samgtu.tasker.api.exception.NotFoundInventoryException;
import ru.samgtu.tasker.entity.*;
import ru.samgtu.tasker.mapper.TaskMapper;
import ru.samgtu.tasker.repository.EmployeeRepository;
import ru.samgtu.tasker.repository.InventoryRepository;
import ru.samgtu.tasker.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final InventoryRepository inventoryRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, EmployeeRepository employeeRepository, InventoryRepository inventoryRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.inventoryRepository = inventoryRepository;
        this.taskMapper = taskMapper;
    }

    public TaskResponseDTO createTask(TaskCreateRequestDTO request) throws NotFoundEmployeeException, NotFoundInventoryException {
        TaskEntity task = taskMapper.toEntity(request);
        EmployeeEntity employee = employeeRepository.findById(request.getEmployeeId()).orElseThrow(NotFoundEmployeeException::new);
        InventoryEntity inventory = inventoryRepository.findById(request.getInventoryId()).orElseThrow(NotFoundInventoryException::new);
        List<SkillLevel> skillLevelList = new ArrayList<>();
        for (SkillLevelRequestDTO skillLevelRequest : request.getSkillLevelRequestDTOList()) {
            SkillLevel skillLevel = new SkillLevel();
            TypeEntity typeEntity = new TypeEntity();
            typeEntity.setSkill(skillLevelRequest.getSkill());
            skillLevel.setSkill(typeEntity);
            skillLevel.setLevel(skillLevelRequest.getLevel());
            skillLevelList.add(skillLevel);
        }
        task.setRequiredSkillLevelList(skillLevelList);
        task.setEmployeeEntity(employee);
        task.setInventoryEntity(inventory);
        employee.addTask(task);
        inventory.addTask(task);
        employeeRepository.save(employee);
        inventoryRepository.save(inventory);
        taskRepository.save(task);
        return taskMapper.toResponse(task);
    }
}
