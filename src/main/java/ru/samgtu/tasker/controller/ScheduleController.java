package ru.samgtu.tasker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.samgtu.tasker.api.dto.response.ScheduleResponseDTO;
import ru.samgtu.tasker.api.exception.NotFoundEmployeeException;
import ru.samgtu.tasker.api.exception.NotFoundInventoryException;
import ru.samgtu.tasker.api.exception.NotFoundMachineException;
import ru.samgtu.tasker.service.ScheduleService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping()
    public ResponseEntity<ScheduleResponseDTO> createSchedule() {
        return ResponseEntity.ok(scheduleService.createSchedule());
    }
}
