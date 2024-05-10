package ru.samgtu.tasker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.samgtu.tasker.api.dto.response.ScheduleResponseDTO;
import ru.samgtu.tasker.entity.EmployeeEntity;
import ru.samgtu.tasker.entity.SheduleEntity;
import ru.samgtu.tasker.entity.SkillLevel;
import ru.samgtu.tasker.entity.TaskEntity;
import ru.samgtu.tasker.mapper.TaskMapper;
import ru.samgtu.tasker.repository.ScheduleRepository;
import ru.samgtu.tasker.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ScheduleService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ScheduleRepository scheduleRepository;
    @Autowired
    public ScheduleService(TaskRepository taskRepository, TaskMapper taskMapper, ScheduleRepository scheduleRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDTO createSchedule() {
        List<TaskEntity> taskEntityList = taskRepository.findAll();
        List<TaskEntity> scheduledTasks = new ArrayList<>();
        for (TaskEntity task : taskEntityList) {
            EmployeeEntity employee = task.getEmployeeEntity();
            int requiredMatches = task.getRequiredSkillLevelList().size();
            int matches = 0;
            for (SkillLevel employeeSkillLevel : employee.getSkillLevelList()) {
                for (SkillLevel taskSkillLevel : task.getRequiredSkillLevelList()) {
                    if (Objects.equals(employeeSkillLevel.getSkill().getSkill(), taskSkillLevel.getSkill().getSkill())
                            && employeeSkillLevel.getLevel() >= taskSkillLevel.getLevel()) {
                        matches++;
                    }
                }
            }
            if (requiredMatches == matches) {
                scheduledTasks.add(task);
                task.setIsScheduled(true);
                taskRepository.save(task);
            }
        }
        SheduleEntity shedule = new SheduleEntity();
        shedule.setTaskEntityList(scheduledTasks);
        scheduleRepository.save(shedule);

        return new ScheduleResponseDTO(taskMapper.toResponseList(scheduledTasks));
    }

}
