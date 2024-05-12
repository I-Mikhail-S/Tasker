package ru.samgtu.tasker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.samgtu.tasker.api.dto.response.ScheduleResponseDTO;
import ru.samgtu.tasker.api.exception.WrongAppointmentTime;
import ru.samgtu.tasker.entity.EmployeeEntity;
import ru.samgtu.tasker.entity.SheduleEntity;
import ru.samgtu.tasker.entity.SkillLevel;
import ru.samgtu.tasker.entity.TaskEntity;
import ru.samgtu.tasker.mapper.TaskMapper;
import ru.samgtu.tasker.repository.ScheduleRepository;
import ru.samgtu.tasker.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    // соотнесение по скилам ------
    // соотнесение по времени -------
    // решение конфликтов
    // -- удаление задачи и отчёт об этом -------
    // -- перепланирование ???

    public ScheduleResponseDTO createSchedule() throws WrongAppointmentTime {
        List<TaskEntity> taskEntityList = taskRepository.findAll();
        List<TaskEntity> scheduledTasks = new ArrayList<>();

        for (TaskEntity task : taskEntityList) {
            if (isMatchingSkillLevel(task)) {
                scheduledTasks.add(task);
                task.setIsScheduled(true);
                taskRepository.save(task);
            }
            else {
                taskRepository.delete(task);
                throw new WrongAppointmentTime();
            }
        }

        SheduleEntity shedule = new SheduleEntity();
        shedule.setTaskEntityList(scheduledTasks);
        scheduleRepository.save(shedule);

        return new ScheduleResponseDTO(taskMapper.toResponseList(scheduledTasks));
    }

    public ScheduleResponseDTO updateSchedule() throws WrongAppointmentTime {
        List<TaskEntity> taskEntityList = taskRepository.findAll();
        List<TaskEntity> scheduledTasks = new ArrayList<>();

        for (TaskEntity task : taskEntityList) {
            if (isMatchingSkillLevel(task) && isFitsInTime(taskEntityList, task)) {
                scheduledTasks.add(task);
                task.setIsScheduled(true);
                taskRepository.save(task);
            }
            else {
                taskRepository.delete(task);
                throw new WrongAppointmentTime();
            }
        }

        SheduleEntity shedule = new SheduleEntity();
        shedule.setTaskEntityList(scheduledTasks);
        scheduleRepository.save(shedule);

        return new ScheduleResponseDTO(taskMapper.toResponseList(scheduledTasks));
    }

    /**
     * Проверка на соответствие скилов назначаемой задачи и исполнителя
     * @param task назначаемая задача
     * @return true - если скил исполнителя такой же или больше, false - если у исполнителя недостаёт навыков
     */
    private boolean isMatchingSkillLevel(TaskEntity task) {
        EmployeeEntity employee = task.getEmployeeEntity();
        int requiredMatches = task.getRequiredSkillLevelList().size();
        int matches = 0;
        for (SkillLevel taskSkillLevel : task.getRequiredSkillLevelList()) {
            for (SkillLevel employeeSkillLevel : employee.getSkillLevelList()) {
                if (Objects.equals(employeeSkillLevel.getSkill().getSkill(), taskSkillLevel.getSkill().getSkill())
                        && employeeSkillLevel.getLevel() >= taskSkillLevel.getLevel()) {
                    matches++;
                    break;
                }
            }
        }
        return requiredMatches == matches;
    }

    /**
     * Проверка на временной промежуток
     * @param taskEntityList все возможные задачи
     * @param checkedTask новая задача
     * @return true - если в расписании конкретного исполнителя есть свободное время на новую задачу
     */
    private boolean isFitsInTime(List<TaskEntity> taskEntityList, TaskEntity checkedTask) {
        List<TaskEntity> sortTaskEntityList = taskEntityList.stream()
                .sorted(Comparator.comparing(TaskEntity::getTimeStart))
                .filter(x -> x.getEmployeeEntity() == checkedTask.getEmployeeEntity())
                .collect(Collectors.toList());

        sortTaskEntityList.remove(checkedTask);

        if (sortTaskEntityList.isEmpty())
            return true;

        for (int i = 0; i < sortTaskEntityList.size() - 1; i++)
            if (checkedTask.getTimeStart().isAfter(sortTaskEntityList.get(i).getTimeEnd()))
                if (checkedTask.getTimeEnd().isBefore(sortTaskEntityList.get(i + 1).getTimeStart()))
                    return true;

        if (checkedTask.getTimeStart().isAfter(sortTaskEntityList.getLast().getTimeEnd()))
            return true;

        return false;
    }

    public List<ScheduleResponseDTO> findAllSchedules() {
        List<SheduleEntity> sheduleEntityList = scheduleRepository.findAll();
        List<ScheduleResponseDTO> scheduleResponseDTOList = new ArrayList<>();

        sheduleEntityList.stream().forEach(x -> scheduleResponseDTOList.add(
                new ScheduleResponseDTO(taskMapper.toResponseList(x.getTaskEntityList()))));

        return scheduleResponseDTOList;
    }

}
