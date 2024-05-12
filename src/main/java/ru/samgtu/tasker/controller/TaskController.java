package ru.samgtu.tasker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.samgtu.tasker.api.dto.request.TaskCreateRequestDTO;
import ru.samgtu.tasker.api.dto.response.TaskResponseDTO;
import ru.samgtu.tasker.api.exception.NotFoundEmployeeException;
import ru.samgtu.tasker.api.exception.NotFoundInventoryException;
import ru.samgtu.tasker.service.TaskService;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping()
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskCreateRequestDTO taskRequest)
            throws NotFoundInventoryException, NotFoundEmployeeException {
        return ResponseEntity.ok(taskService.createTask(taskRequest));
    }
}
