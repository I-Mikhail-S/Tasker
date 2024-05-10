package ru.samgtu.tasker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.samgtu.tasker.api.dto.response.ScheduleResponseDTO;
import ru.samgtu.tasker.api.exception.NotFoundEmployeeException;
import ru.samgtu.tasker.api.exception.NotFoundInventoryException;
import ru.samgtu.tasker.api.exception.NotFoundMachineException;
import ru.samgtu.tasker.service.ScheduleService;

import java.io.IOException;
import java.util.List;

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

    @GetMapping()
    public ResponseEntity<List<ScheduleResponseDTO>> findAllSchedules() {
        return ResponseEntity.ok(scheduleService.findAllSchedules());
    }
}
